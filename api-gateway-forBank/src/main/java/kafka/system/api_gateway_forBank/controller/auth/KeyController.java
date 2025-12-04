package kafka.system.api_gateway_forBank.controller.auth;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

@Deprecated
@RequestMapping("/auth")
public class KeyController {

    @GetMapping("/public-key")
    public String getPublicKey() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("keys/public.pem");

        if (inputStream == null)
            throw new RuntimeException("File 'keys/public.pem' not found in classpath!");

        return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
    }

}
