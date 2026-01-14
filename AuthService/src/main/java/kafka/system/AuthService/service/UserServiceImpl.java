package kafka.system.AuthService.service;


import kafka.system.AuthService.persistence.model.UserCredential;
import kafka.system.AuthService.persistence.repository.UserRepository;
import kafka.system.AuthService.security.JwtService;
import kafka.system.core.dto.AuthService.UserCredentialDto;
import kafka.system.core.dto.AuthService.UserLoginRequest;
import kafka.system.core.dto.AuthService.UserRegisterRequest;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;


@Service
public class UserServiceImpl {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final UserRepository userRepository;
    private final UtilService utilService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtProvider;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final ModelMapper modelMapper;

    @Value("${create-user-events-topic}")
    private String userCreateTopic;


    public UserServiceImpl(UserRepository userRepository, UtilService utilService, PasswordEncoder passwordEncoder, JwtService jwtProvider, KafkaTemplate<String, Object> kafkaTemplate, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.utilService = utilService;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
        this.kafkaTemplate = kafkaTemplate;
        this.modelMapper = modelMapper;
    }

    public Map<String, String> login(UserLoginRequest request) {
        UserCredential user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("invalid credentials"));
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword()))
            throw new IllegalArgumentException("invalid credentials");

        String access = jwtProvider.generateAccessToken(user.getId(), user.getEmail(), user.getRole());
        String refresh = jwtProvider.generateRefreshToken(user.getId(), user.getEmail(), user.getRole());

        return Map.of("access_token", access, "refresh_token", refresh);
    }

    @Transactional("transactionManager")
    public Map<String, String> save(UserRegisterRequest request) {
        if(userRepository.existsByEmail(request.getEmail()))
            throw new IllegalArgumentException("Email already in use");

        String password = passwordEncoder.encode(request.getPassword());
        UserCredential user = new UserCredential(
                request.getUsername(),
                password,
                request.getEmail()
        );

        userRepository.save(user);

        String access = jwtProvider.generateAccessToken(user.getId(), user.getEmail(), user.getRole());
        String refresh = jwtProvider.generateRefreshToken(user.getId(), user.getEmail(), user.getRole());

        postCreateUser(user);
        utilService.postCreateDefaultAcc(user.getId());
        return Map.of("access_token", access, "refresh_token", refresh);
    }

    private void postCreateUser(UserCredential user){
        UserCredentialDto request = new UserCredentialDto();
        request.setId(user.getId());
        request.setTimeStamp(user.getTimeStamp());

        kafkaTemplate.send(userCreateTopic, request)
                .whenComplete((result, exception) -> {
                    if (exception == null) {
                        LOGGER.info("Message sent successfully to Kafka topic '{}'", userCreateTopic);
                    } else {
                        LOGGER.error("Failed to send message to Kafka topic '{}'", userCreateTopic, exception);
                    }
                });
    }

}
