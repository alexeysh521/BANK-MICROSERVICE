package kafka.system.AccountService.handle;

import kafka.system.AccountService.service.AccountServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@KafkaListener(topics = {"create-default-account-event-topic"})
public class AuthServiceEventHandler {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final AccountServiceImpl accountService;

    public AuthServiceEventHandler(AccountServiceImpl accountService) {
        this.accountService = accountService;
    }

    @KafkaHandler
    public void createDefaultAccount(UUID userId) {
        accountService.createDefaultAccount(userId);
        LOGGER.info("Create default account for id={}", userId);
    }
}
