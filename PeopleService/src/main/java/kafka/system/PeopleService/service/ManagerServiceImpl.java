package kafka.system.PeopleService.service;

import kafka.system.PeopleService.persistence.model.Manager;
import kafka.system.PeopleService.persistence.repository.ManagerRepository;
import kafka.system.core.dto.AuthService.ManagerCredentialDto;
import org.springframework.stereotype.Service;

@Service
public class ManagerServiceImpl {

    private final ManagerRepository managerRepository;

    public ManagerServiceImpl(ManagerRepository managerRepository) {
        this.managerRepository = managerRepository;
    }

    public void register(ManagerCredentialDto dto){
        Manager manager = new Manager(
                dto.getId(),
                dto.getTimeStamp()
        );

        managerRepository.save(manager);
    }
}
