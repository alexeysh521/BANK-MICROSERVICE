package kafka.system.AuthService.service;

import kafka.system.AuthService.persistence.model.ManagerCredential;
import kafka.system.AuthService.persistence.repository.ManagerRepository;
import kafka.system.AuthService.security.JwtProvider;
import kafka.system.core.dto.AuthService.ManagerCredentialDto;
import kafka.system.core.dto.AuthService.ManagerRegisterRequest;
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
public class ManagerService {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final ManagerRepository managerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final ModelMapper modelMapper;

    @Value("${create-manager-events-topic}")
    private String managerCreateTopic;

    public ManagerService(ManagerRepository managerRepository, PasswordEncoder passwordEncoder, JwtProvider jwtProvider, KafkaTemplate<String, Object> kafkaTemplate, ModelMapper modelMapper) {
        this.managerRepository = managerRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
        this.kafkaTemplate = kafkaTemplate;
        this.modelMapper = modelMapper;
    }

    public Map<String, String> login(ManagerRegisterRequest request) {
        ManagerCredential manager = managerRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("invalid credentials"));
        if (!passwordEncoder.matches(request.getPassword(), manager.getPassword()))
            throw new IllegalArgumentException("invalid credentials");

        String access = jwtProvider.generateAccessToken(manager.getId(), manager.getEmail(), manager.getRole());
        String refresh = jwtProvider.generateRefreshToken(manager.getId(), manager.getEmail(), manager.getRole());

        return Map.of("access_token", access, "refresh_token", refresh);
    }

    @Transactional("transactionManager")
    public Map<String, String> save(ManagerRegisterRequest request) {
        if(managerRepository.existsByEmail(request.getEmail()))
            throw new IllegalArgumentException("Email already in use");

        String password = passwordEncoder.encode(request.getPassword());
        ManagerCredential manager = new ManagerCredential(
                request.getUsername(),
                password,
                request.getEmail()
        );

        managerRepository.save(manager);

        String access = jwtProvider.generateAccessToken(manager.getId(), manager.getEmail(), manager.getRole());
        String refresh = jwtProvider.generateRefreshToken(manager.getId(), manager.getEmail(), manager.getRole());

        postCreateManager(manager);

        return Map.of("access_token", access, "refresh_token", refresh);
    }

    private void postCreateManager(ManagerCredential manager){
        ManagerCredentialDto request = new ManagerCredentialDto();
        request.setId(manager.getId());
        request.setTimeStamp(manager.getTimeStamp());

        kafkaTemplate.send(managerCreateTopic, request)
                .whenComplete((result, exception) -> {
                    if (exception == null) {
                        LOGGER.info("Message sent successfully to Kafka topic '{}'", managerCreateTopic);
                    } else {
                        LOGGER.error("Failed to send message to Kafka topic '{}'", managerCreateTopic, exception);
                    }
                });
    }


}
