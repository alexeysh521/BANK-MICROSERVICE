package kafka.system.AuthService.controller;

import jakarta.validation.Valid;
import kafka.system.AuthService.service.AdminService;
import kafka.system.AuthService.service.ManagerService;
import kafka.system.AuthService.service.UserService;
import kafka.system.core.dto.AuthService.AdminRegisterRequest;
import kafka.system.core.dto.AuthService.ManagerRegisterRequest;
import kafka.system.core.dto.AuthService.UserRegisterRequest;
import kafka.system.core.dto.model.LoginOrRegisterRequest;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final AdminService adminService;
    private final ManagerService managerService;

    public AuthController(UserService authService, AdminService adminService, ManagerService managerService) {
        this.userService = authService;
        this.adminService = adminService;
        this.managerService = managerService;
    }

    @PostMapping("/register/admin")
    public ResponseEntity<?> registerAdmin(@Valid @RequestBody AdminRegisterRequest request) {
        return ResponseEntity.ok(adminService.save(request));
    }

    @PostMapping("/login/admin")
    public ResponseEntity<?> loginAdmin(@Valid @RequestBody AdminRegisterRequest request) {
        return ResponseEntity.ok(adminService.login(request));
    }

    @PostMapping("/register/manager")
    public ResponseEntity<?> registerManager(@Valid @RequestBody ManagerRegisterRequest request) {
        return ResponseEntity.ok(managerService.save(request));
    }

    @PostMapping("/login/manager")
    public ResponseEntity<?> loginManager(@Valid @RequestBody ManagerRegisterRequest request) {
        return ResponseEntity.ok(managerService.login(request));
    }

    @PostMapping("/register/user")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRegisterRequest request) {
        return ResponseEntity.ok(userService.save(request));
    }

    @PostMapping("/login/user")
    public ResponseEntity<?> loginUser(@Valid @RequestBody UserRegisterRequest request) {
        return ResponseEntity.ok(userService.login(request));
    }

    @GetMapping("/public-key")
    public ResponseEntity<String> publicKey() throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("keys/public.pem");

        if (inputStream == null)
            throw new RuntimeException("File 'keys/public.pem' not found in classpath!");

        return ResponseEntity.ok(new String(inputStream.readAllBytes(), StandardCharsets.UTF_8));
    }

}
