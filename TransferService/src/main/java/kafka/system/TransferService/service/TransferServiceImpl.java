package kafka.system.TransferService.service;

import kafka.system.TransferService.persistence.model.Transaction;
import kafka.system.TransferService.persistence.repository.TransferRepository;
import kafka.system.TransferService.service.impl.TransferService;
import kafka.system.core.dto.TransferService.*;
import kafka.system.core.enums.TransactionStatusType;
import kafka.system.core.enums.TransactionType;
import kafka.system.core.exception.TransfersServiceException;
import kafka.system.core.interfaces.TransactionBase;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class TransferServiceImpl implements TransferService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final TransferRepository transferRepository;

    @Value("${withdraw-events-topic}")
    private String withdraw;

    @Value("${deposit-events-topic}")
    private String deposit;

    @Value("${transfer-events-topic}")
    private String transfer;

    public void updateTransactionStatus(UUID id, TransactionStatusType status){
        Transaction tran = transferRepository.findById(id)
                .orElseThrow(() -> new TransfersServiceException("Transaction entity not found"));
        tran.setTransactionStatus(status);
    }

    @Transactional("transactionManager")
    public UUID operation(Object request, String userId) {
        UUID userID = UUID.fromString(userId);
        Object requestWithId = savePendingTransaction(request, userID);
        UUID tranId = ((TransactionBase) requestWithId).getTransactionId();
        String topic;
        TransactionStatusType transactionStatus;

        try {
            topic = switch (request.getClass().getSimpleName()) {
                case "DepositRequest" -> deposit;
                case "WithdrawRequest" -> withdraw;
                case "TransferRequest" -> transfer;
                default -> {
                    updateTransactionStatus(tranId, TransactionStatusType.FAILED);
                    LOGGER.error("Unknown request type: {}", request.getClass().getSimpleName());
                    throw new TransfersServiceException("Unknown request type: " + request.getClass().getSimpleName());
                }
            };

            kafkaTemplate.executeInTransaction(ko -> {
                try{
                    SendResult<String, Object> result = ko.send(topic, requestWithId).get();
                    LOGGER.info("Sent transaction with tranId: {}", tranId);
                    LOGGER.info("Sent transaction result: {}", result);
                    return null;
                }catch(Exception e){
                    updateTransactionStatus(tranId, TransactionStatusType.FAILED);
                    throw new TransfersServiceException(e);
                }
            });

            transactionStatus = transferRepository.findTransactionById(tranId).orElse(null);

            if(transactionStatus == TransactionStatusType.PENDING)
                updateTransactionStatus(tranId, TransactionStatusType.FAILED);

        } catch (Exception e) {
            updateTransactionStatus(tranId, TransactionStatusType.FAILED);
            LOGGER.error("Other error: {}", e.getMessage());
            throw new TransfersServiceException(e);
        }

        return tranId;
    }

    private Object savePendingTransaction(Object request, UUID userId) {
        return switch (request) {
            case DepositRequest requestD -> {
                requestD.setUserId(userId);
                Transaction transaction = new Transaction(
                        requestD.getUserId(),
                        requestD.getAccId(),
                        null,
                        requestD.getAmount(),
                        requestD.getCurrencyType(),
                        TransactionType.DEPOSIT,
                        TransactionStatusType.PENDING,
                        LocalDateTime.now()
                );
                transferRepository.save(transaction);
                requestD.setTransactionId(transaction.getTransferId());

                yield requestD;
            }
            case WithdrawRequest requestW -> {
                requestW.setUserId(userId);
                Transaction transaction = new Transaction(
                        requestW.getUserId(),
                        requestW.getAccId(),
                        null,
                        requestW.getAmount(),
                        requestW.getCurrencyType(),
                        TransactionType.WITHDRAW,
                        TransactionStatusType.PENDING,
                        LocalDateTime.now()
                );
                transferRepository.save(transaction);
                requestW.setTransactionId(transaction.getTransferId());

                yield requestW;
            }
            case TransferRequest requestT -> {
                requestT.setUserId(userId);
                Transaction transaction = new Transaction(
                        requestT.getUserId(),
                        requestT.getFromAccId(),
                        requestT.getToAccId(),
                        requestT.getAmount(),
                        requestT.getCurrencyType(),
                        TransactionType.TRANSFER,
                        TransactionStatusType.PENDING,
                        LocalDateTime.now()
                );
                transferRepository.save(transaction);
                requestT.setTransactionId(transaction.getTransferId());

                yield requestT;
            }
            default -> throw new IllegalArgumentException("Unknown request type");
        };
    }
}
/// Логика: мы отправляем запрос консюмеру и ждем, если ок, мы записываем нашу транзакцию. Нужно отправить сообщение телеграмм
/// с номером транзакции
/// ЗАМЕТКА ПО КАФКА: ЕСЛИ НЕТ АККАУНТА В БД У КОНСЮМЕРА БУДЕТ РЕТРАЙ
/// Здесь мы Не делаем логику списания денег или пополнения
/// Мы просто отправляем запрос на другой микросервис который уже занимается логикой и сохраняет баланс пользователя
/// В этом микросервисе мы записываем в базу данных операции
/// Короче здесь будет бд с операциями, которые производятся с аккаунтом, не более

