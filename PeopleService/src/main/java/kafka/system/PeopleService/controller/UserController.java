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
    public ResponseEntity<String> addData(@RequestHeader(value = "X-User-Id", required = false) String userId,
                                          @RequestBody AddUserDataDto dto){
        userService.addData(dto, userId);
        return ResponseEntity.ok("Added data successfully");
    }

    /// изменить личные данные
    @PutMapping("/changeData")
    public ResponseEntity<String> changeData(@RequestHeader(value = "X-User-Id", required = false) String userId,
                                             @RequestBody AddUserDataDto dto){
        userService.changeData(dto, userId);
        return ResponseEntity.ok("Change data successfully");
    }

    /// просмотр даных пользователя
    @PostMapping("/view/data")
    public ResponseEntity<ViewDataUser> viewData(@RequestHeader(value = "X-User-Id", required = false) String userId){
        return ResponseEntity.ok(userService.viewData(UUID.fromString(userId)));
    }

}
