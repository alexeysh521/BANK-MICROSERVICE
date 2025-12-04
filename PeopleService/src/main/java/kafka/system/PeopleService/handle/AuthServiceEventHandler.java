package kafka.system.PeopleService.handle;

import kafka.system.PeopleService.service.AdminServiceImpl;
import kafka.system.PeopleService.service.ManagerServiceImpl;
import kafka.system.PeopleService.service.UserServiceImpl;
import kafka.system.core.dto.AuthService.AdminCredentialDto;
import kafka.system.core.dto.AuthService.ManagerCredentialDto;
import kafka.system.core.dto.AuthService.UserCredentialDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@KafkaListener(topics = {"create-admin-events-topic", "create-manager-events-topic", "create-user-events-topic"})
public class AuthServiceEventHandler {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final UserServiceImpl userService;
    private final AdminServiceImpl adminService;
    private final ManagerServiceImpl managerService;

    public AuthServiceEventHandler(UserServiceImpl userService, AdminServiceImpl adminService, ManagerServiceImpl managerService) {
        this.userService = userService;
        this.adminService = adminService;
        this.managerService = managerService;
    }

    @KafkaHandler
    public void createAdmin(AdminCredentialDto dto){
        adminService.register(dto);
        LOGGER.info("✅Received data from AdminCredentialDto successfully");
    }

    @KafkaHandler
    public void createManager(ManagerCredentialDto dto){
        managerService.register(dto);
        LOGGER.info("✅Received data from ManagerCredentialDto successfully");
    }

    @KafkaHandler
    public void createUser(UserCredentialDto dto){
        userService.register(dto);
        LOGGER.info("✅Received data from UserCredentialDto successfully");
    }

}
