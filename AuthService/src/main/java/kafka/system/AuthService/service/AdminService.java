package kafka.system.AuthService.service;

import kafka.system.AuthService.persistence.model.AdminCredential;
import kafka.system.AuthService.persistence.repository.AdminRepository;
import kafka.system.AuthService.security.JwtProvider;
import kafka.system.core.dto.AuthService.AdminCredentialDto;
import kafka.system.core.dto.AuthService.AdminRegisterRequest;
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
public class AdminService {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final ModelMapper modelMapper;

    @Value("${create-admin-events-topic}")
    private String adminCreateTopic;

    public AdminService(AdminRepository adminRepository, PasswordEncoder passwordEncoder, JwtProvider jwtProvider, KafkaTemplate<String, Object> kafkaTemplate, ModelMapper modelMapper) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
        this.kafkaTemplate = kafkaTemplate;
        this.modelMapper = modelMapper;
    }

    public Map<String, String> login(AdminRegisterRequest request) {
        AdminCredential admin = adminRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("invalid credentials"));
        if (!passwordEncoder.matches(request.getPassword(), admin.getPassword()))
            throw new IllegalArgumentException("invalid credentials");

        String access = jwtProvider.generateAccessToken(admin.getId(), admin.getEmail(), admin.getRole());
        String refresh = jwtProvider.generateRefreshToken(admin.getId(), admin.getEmail(), admin.getRole());

        return Map.of("access_token", access, "refresh_token", refresh);
    }

    @Transactional("transactionManager")
    public Map<String, String> save(AdminRegisterRequest request) {
        if(adminRepository.existsByEmail(request.getEmail()))
            throw new IllegalArgumentException("Email already in use");

        String password = passwordEncoder.encode(request.getPassword());
        AdminCredential admin = new AdminCredential(
                request.getUsername(),
                password,
                request.getEmail()
        );

        adminRepository.save(admin);

        String access = jwtProvider.generateAccessToken(admin.getId(), admin.getEmail(), admin.getRole());
        String refresh = jwtProvider.generateRefreshToken(admin.getId(), admin.getEmail(), admin.getRole());

        postCreateAdmin(admin);

        return Map.of("access_token", access, "refresh_token", refresh);
    }

    private void postCreateAdmin(AdminCredential admin){
        AdminCredentialDto request = new AdminCredentialDto();
        request.setId(admin.getId());
        request.setTimeStamp(admin.getTimeStamp());

        kafkaTemplate.send(adminCreateTopic, request)
                .whenComplete((result, exception) -> {
                    if (exception == null) {
                        LOGGER.info("Message sent successfully to Kafka topic '{}'", adminCreateTopic);
                    } else {
                        LOGGER.error("Failed to send message to Kafka topic '{}'", adminCreateTopic, exception);
                    }
                });
    }
}
