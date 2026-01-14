package kafka.system.PeopleService.controller;

import kafka.system.PeopleService.service.UserServiceImpl;
import kafka.system.core.dto.AuthService.ViewDataUserRequest;
import kafka.system.core.dto.PeopleService.AddUserDataDto;
import kafka.system.core.dto.PeopleService.ViewDataUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserServiceImpl userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    /// ввести личные данные
    @PostMapping("/addData")
    public ResponseEntity<String> addData(@RequestBody AddUserDataDto dto){
        userService.addData(dto);
        return ResponseEntity.ok("Added data successfully");
    }

    /// изменить личные данные
    @PutMapping("/changeData")
    public ResponseEntity<String> changeData(@RequestBody AddUserDataDto dto){
        userService.changeData(dto);
        return ResponseEntity.ok("Change data successfully");
    }

    /// просмотр даных пользователя
    @PostMapping("/view/data")
    public ResponseEntity<ViewDataUser> viewData(@RequestBody ViewDataUserRequest request){
        return ResponseEntity.ok(userService.viewData(request.getId()));
    }

}
