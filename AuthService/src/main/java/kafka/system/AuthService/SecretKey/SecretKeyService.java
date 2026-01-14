package kafka.system.AuthService.SecretKey;

import jakarta.persistence.EntityNotFoundException;
import kafka.system.core.dto.AuthService.SecretKeyDto;
import kafka.system.core.dto.model.AccountMap;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class SecretKeyService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final SecretKeyRepository secretKeyRepository;
    private final ModelMapper modelMapper;

    public SecretKeyService(SecretKeyRepository secretKeyRepository, ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        startPolling();
        this.secretKeyRepository = secretKeyRepository;
    }

    public SecretKeyDto viewKey(){
        return secretKeyRepository.findById(1L)
                .stream()
                .map(this::toConvert)
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException(""));
    }

    public void manualGenerateSecretKey(){
        secretKeyRepository.updateSecretKey(generateSecretKey());
    }

    private void startPolling() {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(() -> {
            try{
                secretKeyRepository.updateSecretKey(generateSecretKey());
                LOGGER.info("Updating secretKey successfully");
            }catch (Exception e){
                LOGGER.error("Error updating secretKey");
            }
        }, 10, 10, TimeUnit.MINUTES);
    }

    public static String generateSecretKey() {
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 10; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    public boolean checkSecretKey(String secretKey) {
        return secretKeyRepository.existsByValue(secretKey);
    }

    private SecretKeyDto toConvert(SecretKey secretKey) {
        return modelMapper.map(secretKey, SecretKeyDto.class);
    }
}
