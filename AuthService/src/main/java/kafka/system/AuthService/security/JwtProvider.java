package kafka.system.AuthService.security;

import io.jsonwebtoken.*;
import jakarta.annotation.PostConstruct;
import kafka.system.core.enums.RolesType;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtProvider {
    private RSAPrivateKey privateKey;
    private RSAPublicKey publicKey;

    @PostConstruct
    public void init() throws Exception {
        this.privateKey = findPrivateKeys();
        this.publicKey = findPublicKeys();
    }

    private RSAPrivateKey findPrivateKeys() throws Exception {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("keys/private.pem")) {
            if (inputStream == null) throw new RuntimeException("File not found: keys/private.pem");
            return JwtKeyUtil.readPrivateKey(inputStream);
        }
    }

    private RSAPublicKey findPublicKeys() throws Exception {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("keys/public.pem");
        if (inputStream == null) throw new RuntimeException("File not found: " + "keys/public.pem");

        return JwtKeyUtil.readPublicKey(inputStream);
    }

    public String generateAccessToken(UUID userId, String email, RolesType role) {
        Instant now = Instant.now();
        return Jwts.builder()
                .setSubject(userId.toString())
                .claim("email", email)
                .claim("role", role)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(15, ChronoUnit.MINUTES)))
                .signWith(privateKey, SignatureAlgorithm.RS256)
                .compact();
    }

    public String generateRefreshToken(UUID userId, String email, RolesType role) {
        Instant now = Instant.now();
        return Jwts.builder()
                .setSubject(userId.toString())
                .claim("email", email)
                .claim("role", role)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(30, ChronoUnit.DAYS)))
                .signWith(privateKey, SignatureAlgorithm.RS256)
                .compact();
    }

    public Jws<Claims> validateToken(String token) {
        return Jwts.parserBuilder().setSigningKey(publicKey).build().parseClaimsJws(token);
    }

    public RSAPublicKey getPublicKey() {
        return publicKey;
    }
}
