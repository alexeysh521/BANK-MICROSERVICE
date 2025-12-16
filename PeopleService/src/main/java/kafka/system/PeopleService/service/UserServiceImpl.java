package kafka.system.PeopleService.service;

import jakarta.persistence.EntityNotFoundException;
import kafka.system.PeopleService.persistence.model.User;
import kafka.system.PeopleService.persistence.repository.UserRepository;
import kafka.system.core.dto.AuthService.UserCredentialDto;
import kafka.system.core.dto.PeopleService.AddUserDataDto;
import kafka.system.core.dto.PeopleService.ViewDataUser;
import kafka.system.core.exception.TransfersServiceException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
public class UserServiceImpl {

    @Value("${account-status-changed-topic}")
    private String accStatusChangedTopic;

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final UserRepository userRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, KafkaTemplate<String, Object> kafkaTemplate, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.modelMapper = modelMapper;
    }

    public ViewDataUser viewData(UUID userId){
        User user = findUserByIdOrThrowException(userId);
        return convertTo(user);
    }

    public void changeData(AddUserDataDto dto){
        User user = findUserByIdOrThrowException(dto.getId());

        user.setFirstName(dto.getFirstName());
        user.setOverageName(dto.getOverageName());
        user.setLastName(dto.getLastName());
        user.setPhone(dto.getPhone());
        user.setDateOfBirth(convertLocalDateTime(dto.getDateOfBirth()));
        user.setGender(dto.getGender());
        user.setPassportNumber(dto.getPassportNumber());
        user.setPassportSerial(dto.getPassportSerial());

        userRepository.save(user);
    }

    @Transactional("transactionManager")
    public void addData(AddUserDataDto dto){
        User user = findUserByIdOrThrowException(dto.getId());

        user.setFirstName(dto.getFirstName());
        user.setOverageName(dto.getOverageName());
        user.setLastName(dto.getLastName());
        user.setPhone(dto.getPhone());
        user.setDateOfBirth(convertLocalDateTime(dto.getDateOfBirth()));
        user.setGender(dto.getGender());
        user.setPassportNumber(dto.getPassportNumber());
        user.setPassportSerial(dto.getPassportSerial());

        kafkaTemplate.executeInTransaction(ko -> {
            try{
                SendResult<String, Object> result = ko.send(accStatusChangedTopic, user.getId()).get();
                LOGGER.info("Send result for adding data for user: {}", result);
                return null;
            }catch(Exception e){
                throw new TransfersServiceException(e);
            }
        });

        userRepository.save(user);
    }

    public void register(UserCredentialDto dto){
        User user = new User(
                dto.getId(),
                dto.getTimeStamp()
        );

        userRepository.save(user);
    }

    private User findUserByIdOrThrowException(UUID userId) {
        return userRepository.findById(userId).orElseThrow(()
                -> new EntityNotFoundException("User not found"));
    }

    private LocalDateTime convertLocalDateTime(String localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate localDate = LocalDate.parse(localDateTime, formatter);
        return localDate.atStartOfDay();
    }

    private ViewDataUser convertTo(User user){
        return new ModelMapper().map(user, ViewDataUser.class);
    }
}
