package kafka.system.PeopleService.service;

import kafka.system.PeopleService.persistence.model.User;
import kafka.system.PeopleService.persistence.repository.UserRepository;
import kafka.system.core.dto.AuthService.UserCredentialDto;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void register(UserCredentialDto dto){
        User user = new User(
                dto.getId(),
                dto.getTimeStamp()
        );

        userRepository.save(user);
    }
}
