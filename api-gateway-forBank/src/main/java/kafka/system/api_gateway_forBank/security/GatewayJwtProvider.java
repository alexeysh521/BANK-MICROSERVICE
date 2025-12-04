package kafka.system.api_gateway_forBank.security;


import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.security.interfaces.RSAPublicKey;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class GatewayJwtProvider {

    private final AtomicReference<RSAPublicKey> publicKeyRef = new AtomicReference<>();

    public void setPublicKey(RSAPublicKey key) {
        this.publicKeyRef.set(key);
    }

    public Jws<Claims> validateToken(String token) {
        RSAPublicKey pub = publicKeyRef.get();
        if (pub == null) {
            throw new JwtException("public key not loaded");
        }
        return Jwts.parserBuilder().setSigningKey(pub).build().parseClaimsJws(token);
    }

}
