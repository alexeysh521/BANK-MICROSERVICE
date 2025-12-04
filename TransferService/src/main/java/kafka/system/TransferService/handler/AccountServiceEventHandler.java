package kafka.system.TransferService.handler;


import kafka.system.TransferService.persistence.model.Transaction;
import kafka.system.TransferService.persistence.repository.TransferRepository;
import kafka.system.core.dto.AccountService.TranConfirmed;
import kafka.system.core.dto.AccountService.TranFailure;
import kafka.system.core.enums.TransactionStatusType;
import kafka.system.core.exception.TransfersServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@KafkaListener(topics = {"transfer-confirmed-topic", "transfer-failure-topic"})
public class AccountServiceEventHandler {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final TransferRepository transferRepository;

    public AccountServiceEventHandler(TransferRepository transferRepository) {
        this.transferRepository = transferRepository;
    }

    @KafkaHandler
    @Transactional("transactionManager")
    public void transferConfirmed(TranConfirmed dto) {
        try {
            Transaction tran = transferRepository.findById(dto.getId())
                    .orElseThrow(() -> new TransfersServiceException("Transaction entity not found"));
            tran.setTransactionStatus(TransactionStatusType.SUCCESS);
            LOGGER.info("Transaction confirmed with id: {}", dto.getId());
            transferRepository.save(tran);
        }catch(TransfersServiceException e) {
            LOGGER.warn("Transaction confirmed not found, might be duplicate message: {}", dto.getId());
        }
    }

    @KafkaHandler
    @Transactional("transactionManager")
    public void transferFailed(TranFailure dto) {
        try {
            Transaction tran = transferRepository.findById(dto.getId())
                    .orElseThrow(() -> new TransfersServiceException("Transaction entity not found"));
            tran.setTransactionStatus(TransactionStatusType.FAILED);
            LOGGER.error("Transaction failed with id: {}", dto.getId());
            transferRepository.save(tran);
        }catch(TransfersServiceException e) {
            LOGGER.warn("Transaction failed not found, might be duplicate message: {}", dto.getId());
        }

    }
}
