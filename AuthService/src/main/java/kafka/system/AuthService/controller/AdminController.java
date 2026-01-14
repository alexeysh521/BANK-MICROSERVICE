package kafka.system.AuthService.controller;

import jakarta.ws.rs.PathParam;
import kafka.system.AuthService.SecretKey.SecretKeyService;
import kafka.system.AuthService.service.AdminServiceImpl;
import kafka.system.core.dto.AuthService.ViewSecretKeyDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/admins")
public class AdminController {

    private final SecretKeyService secretKeyService;
    private final AdminServiceImpl adminService;

    public AdminController(SecretKeyService secretKeyService, AdminServiceImpl adminService) {
        this.secretKeyService = secretKeyService;
        this.adminService = adminService;
    }

    @PostMapping("/view/secretKey")
    public ResponseEntity<?> viewSecretKey(@RequestBody ViewSecretKeyDto dto){
        if(adminService.checkPassword(dto.getEmail(), dto.getPassword()))
            return ResponseEntity.badRequest().body("Invalid password");

        return ResponseEntity.ok(secretKeyService.viewKey());
    }

    @GetMapping("/public-key")
    public ResponseEntity<String> publicKey() throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("keys/public.pem");

        if (inputStream == null)
            throw new RuntimeException("File 'keys/public.pem' not found in classpath!");

        return ResponseEntity.ok(new String(inputStream.readAllBytes(), StandardCharsets.UTF_8));
    }

    @GetMapping("/generate/secretKey")
    public ResponseEntity<String> generateSecretKey(){
        secretKeyService.manualGenerateSecretKey();
        return ResponseEntity.ok("Manual generate secretKey successfully");
    }
}
