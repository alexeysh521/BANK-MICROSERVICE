package kafka.system.api_gateway_forBank.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import java.io.InputStream;
import java.security.interfaces.RSAPublicKey;

@Component
public class JwtUtil {

    private RSAPublicKey publicKey;

    @PostConstruct
    public void init() throws Exception {
        this.publicKey = findPublicKeys();
    }

    private RSAPublicKey findPublicKeys() throws Exception {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("keys/public.pem");
        if (inputStream == null) throw new RuntimeException("File not found: " + "keys/public.pem");

        return JwtKeyUtil.readPublicKey(inputStream);
    }

    public Jws<Claims> validateToken(String token) {
        return Jwts.parserBuilder().setSigningKey(publicKey).build().parseClaimsJws(token);
    }
}
