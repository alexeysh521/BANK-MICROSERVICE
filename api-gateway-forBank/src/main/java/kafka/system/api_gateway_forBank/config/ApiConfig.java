package kafka.system.api_gateway_forBank.config;

import kafka.system.api_gateway_forBank.security.JwtAuthFilter;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ApiConfig {

    @Bean
    @LoadBalanced
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

    private final JwtAuthFilter jwtAuthFilter;

    public ApiConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("auth-service", r -> r
                        .path("/auth/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://AUTH-SERVICE"))

                .route("transfer-service", r -> r
                        .path("/transfer/**")
                        .filters(f -> f
                                .filter(jwtAuthFilter)
                                .stripPrefix(1))
                        .uri("lb://TRANSFER-SERVICE"))
                .build();
    }

}
