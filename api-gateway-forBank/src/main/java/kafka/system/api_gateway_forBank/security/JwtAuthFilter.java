package kafka.system.api_gateway_forBank.security;

import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import org.springframework.http.server.reactive.ServerHttpRequest;

@Component
public class JwtAuthFilter extends AbstractGatewayFilterFactory<JwtAuthFilter.Config> {

    private final PublicKeyProvider publicKeyProvider;
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public JwtAuthFilter(PublicKeyProvider publicKeyProvider) {
        super(Config.class);
        this.publicKeyProvider = publicKeyProvider;
    }

    public static class Config{}

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            LOGGER.info("✅ JwtAuthFilter работает!");

            if (exchange.getRequest().getURI().getPath().startsWith("/auth/")) {
                return chain.filter(exchange);
            }

            var request = exchange.getRequest();
            String token = extractToken(request);

            if (token == null) {
                return unauthorized(exchange);
            }

            Claims claims;
            try {
                claims = Jwts.parserBuilder()
                        .setSigningKey(publicKeyProvider.getPublicKey())
                        .build()
                        .parseClaimsJws(token)
                        .getBody();
            } catch (Exception e) {
                return unauthorized(exchange);
            }

            var userId = claims.getSubject();
            var role = claims.get("role", String.class);

            ServerHttpRequest mutated = request.mutate()
                    .header("X-User-Id", userId)
                    .header("X-User-Role", role)
                    .build();

            return chain.filter(exchange.mutate().request(mutated).build());
        };
    }

    private String extractToken(ServerHttpRequest req) {
        var header = req.getHeaders().getFirst("Authorization");
        if (header != null && header.startsWith("Bearer "))
            return header.substring(7);
        return null;
    }

    private Mono<Void> unauthorized(ServerWebExchange ex) {
        ex.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return ex.getResponse().setComplete();
    }
}
