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

}
