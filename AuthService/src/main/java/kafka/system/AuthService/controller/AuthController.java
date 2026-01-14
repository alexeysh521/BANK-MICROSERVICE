package kafka.system.AuthService.controller;

import jakarta.validation.Valid;
import kafka.system.AuthService.SecretKey.SecretKeyService;
import kafka.system.AuthService.service.AdminServiceImpl;
import kafka.system.AuthService.service.ManagerServiceImpl;
import kafka.system.AuthService.service.UserServiceImpl;
import kafka.system.core.dto.AuthService.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserServiceImpl userService;
    private final AdminServiceImpl adminService;
    private final ManagerServiceImpl managerService;
    private final SecretKeyService secretKeyService;

    public AuthController(UserServiceImpl authService, AdminServiceImpl adminService, ManagerServiceImpl managerService, SecretKeyService secretKeyService) {
        this.userService = authService;
        this.adminService = adminService;
        this.managerService = managerService;
        this.secretKeyService = secretKeyService;
    }

    @PostMapping("/register/admin")
    public ResponseEntity<?> registerAdmin(@Valid @RequestBody AdminRegisterRequest request) {
        if(!secretKeyService.checkSecretKey(request.getSecretKey()))
            return ResponseEntity.badRequest().body("Invalid secret key");
        return ResponseEntity.ok(adminService.save(request));
    }

    @PostMapping("/login/admin")
    public ResponseEntity<?> loginAdmin(@Valid @RequestBody AdminLoginRequest request) {
        if(!secretKeyService.checkSecretKey(request.getSecretKey()))
            return ResponseEntity.badRequest().body("Invalid secret key");
        return ResponseEntity.ok(adminService.login(request));
    }

    @PostMapping("/register/manager")
    public ResponseEntity<?> registerManager(@Valid @RequestBody ManagerRegisterRequest request) {
        if(!secretKeyService.checkSecretKey(request.getSecretKey()))
            return ResponseEntity.badRequest().body("Invalid secret key");
        return ResponseEntity.ok(managerService.save(request));
    }

    @PostMapping("/login/manager")
    public ResponseEntity<?> loginManager(@Valid @RequestBody ManagerLoginRequest request) {
        if(!secretKeyService.checkSecretKey(request.getSecretKey()))
            return ResponseEntity.badRequest().body("Invalid secret key");
        return ResponseEntity.ok(managerService.login(request));
    }

    @PostMapping("/register/user")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRegisterRequest request) {
        return ResponseEntity.ok(userService.save(request));
    }

    @PostMapping("/login/user")
    public ResponseEntity<?> loginUser(@Valid @RequestBody UserLoginRequest request) {
        return ResponseEntity.ok(userService.login(request));
    }
}
