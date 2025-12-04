package kafka.system.PeopleService.controller;

import kafka.system.PeopleService.service.ManagerServiceImpl;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/people/manager")
public class ManagerController {

    private final ManagerServiceImpl managerService;

    public ManagerController(ManagerServiceImpl managerService) {
        this.managerService = managerService;
    }

//    @PostMapping("/register9570427e9-257ba")
//    public ResponseEntity<String> register(@RequestBody ManagerRegister dto){
//        managerService.register(dto);
//        return ResponseEntity.ok("Register successful");
//    }

    /// вход

    /// отправляется сообщение на emailNotificationService с kafka

    /// ввести личные данные

    /// изменить личные данные

    /// сменить логин

    /// сменить пароль

}
