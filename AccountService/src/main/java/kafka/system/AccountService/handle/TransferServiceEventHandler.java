package kafka.system.AccountService.handle;


import kafka.system.AccountService.service.AccountServiceImpl;
import kafka.system.core.dto.TransferService.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Service
@KafkaListener(topics = {"deposit-events-topic", "withdraw-events-topic", "transfer-events-topic"})
@RequiredArgsConstructor
public class TransferServiceEventHandler {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final AccountServiceImpl accountService;

    @KafkaHandler
    public void deposit(DepositRequest depositRequest) {
        accountService.deposit(depositRequest);
        LOGGER.info("Received deposit request");
    }

    @KafkaHandler
    public void withdraw(WithdrawRequest withdrawRequest) {
        accountService.withdraw(withdrawRequest);
        LOGGER.info("Received withdraw request");
    }

    @KafkaHandler
    public void transfer(TransferRequest transferRequest) {
        accountService.transfer(transferRequest);
        LOGGER.info("Received transfer request");
    }

}
