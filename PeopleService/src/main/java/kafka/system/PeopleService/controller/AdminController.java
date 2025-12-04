package kafka.system.PeopleService.controller;

import kafka.system.PeopleService.service.AdminServiceImpl;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.http.HttpResponse;

@RestController
@RequestMapping("/people/admin")
public class AdminController {

    private final AdminServiceImpl adminService;

    public AdminController(AdminServiceImpl adminService) {
        this.adminService = adminService;
    }

    //как лучше передавать данные по топикам (json or String Format Json)
    //регистрация админа (с помощью API Gateway)
//    @PostMapping("/register9590727e9-288sh")
//    public ResponseEntity<String> register(@RequestBody AdminRegister dto){
//        adminService.register(dto);
//        return ResponseEntity.ok("Register successful");
//    }

    /// вход

    /// отправляется сообщение на emailNotificationService с kafka

    /// ввести личные данные

    /// изменить личные данные

    /// сменить логин

    /// сменить пароль
}
