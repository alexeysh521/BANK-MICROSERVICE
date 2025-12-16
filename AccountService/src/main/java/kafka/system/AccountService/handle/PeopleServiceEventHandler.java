package kafka.system.AccountService.handle;

import jakarta.persistence.EntityNotFoundException;
import kafka.system.AccountService.persistence.model.Account;
import kafka.system.AccountService.persistence.repository.AccountRepository;
import kafka.system.core.enums.AccountStatusType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
@KafkaListener(topics = {"account-status-changed-topic"})
public class PeopleServiceEventHandler {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final AccountRepository accountRepository;

    public PeopleServiceEventHandler(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @KafkaHandler
    public void changedStatusAccount(UUID userId){
        Account account = accountRepository.findAccountByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("Not found user with id: " + userId.toString()));

        account.setStatus(AccountStatusType.ACTIVE);
        accountRepository.save(account);
        LOGGER.info("Replace status account for user id={}", userId);
    }
}
