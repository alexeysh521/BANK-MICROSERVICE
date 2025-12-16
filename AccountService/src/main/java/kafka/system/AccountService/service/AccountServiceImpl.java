package kafka.system.AccountService.service;

import jakarta.persistence.EntityNotFoundException;
import kafka.system.AccountService.persistence.model.Account;
import kafka.system.AccountService.persistence.repository.AccountRepository;
import kafka.system.AccountService.service.impl.AccountService;
import kafka.system.core.dto.AccountService.AccountBalanceDto;
import kafka.system.core.dto.AccountService.CreateAcc;
import kafka.system.core.dto.AccountService.TranConfirmed;
import kafka.system.core.dto.AccountService.TranFailure;
import kafka.system.core.dto.TransferService.*;
import kafka.system.core.dto.model.AccountMap;
import kafka.system.core.enums.AccountStatusType;
import kafka.system.core.enums.TransactionType;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final ValidationService validationService;
    private final ModelMapper modelMapper;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Value("${transfer-confirmed-topic}")
    private String confirmedTran;

    @Value("${transfer-failure-topic}")
    private String failureTran;

    public List<AccountMap> findAllByUserId(UUID userId) {

        return accountRepository.findAccountsByUserId(userId)
                .stream()
                .map(this::toConvert)
                .toList();
    }

    public void createAccount(CreateAcc createAcc){
        Account existingAcc = accountRepository.findAccountsByUserId(createAcc.getUserId())
                .stream()
                .filter(acc -> acc.getStatus() == AccountStatusType.ACTIVE || acc.getStatus() == AccountStatusType.PREMIUM)
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Account not found"));

        if(existingAcc.getStatus() == AccountStatusType.PENDING_VERIFICATION || existingAcc.getStatus() == AccountStatusType.BLOCKED)
            throw new IllegalArgumentException("Account always pending or blocked status type");

        Account account = new Account(
                createAcc.getUserId(),
                createAcc.getBalance(),
                createAcc.getCurrency()
        );

        account.setStatus(AccountStatusType.ACTIVE);
        accountRepository.save(account);
    }

    public void createDefaultAccount(UUID userId) {
        Account account = new Account(
                userId
        );
        accountRepository.save(account);
    }

    public AccountMap findAllOnAcc(UUID accId) {
        return accountRepository.findById(accId)
                .stream()
                .map(this::toConvert)
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Account not found"));
    }

    public AccountBalanceDto viewBalance(UUID accId){
        return accountRepository.viewAccBalance(accId);
    }

    @Override
    @Transactional("transactionManager")
    public void deposit(DepositRequest dto){
        try{
            Account a = checkExecuteAndOwnerForAccount(dto.getAccId(), dto.getUserId());
            validationService.validateUserStatusForTransactionBegin(
                    dto.getUserStatus(),
                    TransactionType.DEPOSIT,
                    a.getStatus()
            );

            BigDecimal depositAmount = dto.getCurrencyType().convert(dto.getAmount(), dto.getCurrencyType());
            a.setBalance(a.getBalance().add(depositAmount));

            accountRepository.save(a);
            sendKafkaResult(dto.getTransactionId(), true);
        } catch (IllegalArgumentException e) {
            sendKafkaResult(dto.getTransactionId(), false);
            LOGGER.error("Transfer failed: {}", e.getMessage());
        } catch(Exception e) {
            sendKafkaResult(dto.getTransactionId(), false);
            LOGGER.error("Unexpected error while processing transfer: {}", e.getMessage(), e);
        }
    }

    @Override
    @Transactional("transactionManager")
    public void withdraw(WithdrawRequest dto){
        try{
            Account a = checkExecuteAndOwnerForAccount(dto.getAccId(), dto.getUserId());
            validationService.validateUserStatusForTransactionBegin(
                    dto.getUserStatus(),
                    TransactionType.WITHDRAW,
                    a.getStatus()
            );

            validationService.checkBalanceV(a.getBalance(), dto.getAmount());

            BigDecimal withdrawAmount = dto.getCurrencyType().convert(dto.getAmount(), dto.getCurrencyType());
            a.setBalance(a.getBalance().subtract(withdrawAmount));

            accountRepository.save(a);
            sendKafkaResult(dto.getTransactionId(), true);
        } catch (IllegalArgumentException e) {
            sendKafkaResult(dto.getTransactionId(), false);
            LOGGER.error("Transfer failed: {}", e.getMessage());
        } catch(Exception e) {
            sendKafkaResult(dto.getTransactionId(), false);
            LOGGER.error("Unexpected error while processing transfer: {}", e.getMessage(), e);
        }
    }

    @Override
    @Transactional("transactionManager")
    public void transfer(TransferRequest dto){
        try{
            Account fromAcc = validationService.executeAccount(dto.getFromAccId());
            Account toAcc = validationService.executeAccount(dto.getToAccId());

            TransactionType typeTran = TransactionType.TRANSFER;

            validationService.checkBalanceV(fromAcc.getBalance(), dto.getAmount());

            validationService.checkOwnerForAccount(fromAcc.getUserId(), dto.getUserId());
            validationService.validateUserStatusForTransactionBegin(dto.getUserStatus(), typeTran, fromAcc.getStatus());
            BigDecimal amount = validationService.checkBalance(fromAcc.getBalance(), dto.getAmount());

            BigDecimal convertedAmount = amount;

            if(fromAcc.getCurrency() != toAcc.getCurrency())
                convertedAmount = fromAcc.getCurrency().convert(amount, toAcc.getCurrency());

            fromAcc.setBalance(fromAcc.getBalance().subtract(amount));
            toAcc.setBalance(toAcc.getBalance().add(convertedAmount));

            accountRepository.save(fromAcc);
            accountRepository.save(toAcc);

            sendKafkaResult(dto.getTransactionId(), true);
        } catch (IllegalArgumentException e) {
            sendKafkaResult(dto.getTransactionId(), false);
            LOGGER.error("Transfer failed: {}", e.getMessage());
        } catch(Exception e) {
            sendKafkaResult(dto.getTransactionId(), false);
            LOGGER.error("Unexpected error while processing transfer: {}", e.getMessage(), e);
        }
    }

    private void sendKafkaResult(UUID transactionId, boolean success) {
        try{
            if(success){
                kafkaTemplate.send(confirmedTran, new TranConfirmed(transactionId));
                LOGGER.info("Transaction confirmed with transaction id: {}", transactionId);
            }else{
                kafkaTemplate.send(failureTran, new TranFailure(transactionId));
                LOGGER.info("Transaction failure with transaction id: {}", transactionId);
            }
        }catch (Exception e) {
            LOGGER.error("Failed to send Kafka result for transaction: {}", transactionId);
        }
    }

    private Account checkExecuteAndOwnerForAccount(UUID acc_id, UUID user_id){
        Account account = validationService.executeAccount(acc_id);
        validationService.checkOwnerForAccount(account.getUserId(), user_id);
        return account;
    }

    private AccountMap toConvert(Account account) {
        return modelMapper.map(account, AccountMap.class);
    }
}

/// Здесь мы производим операции над аккаунтом(начисляем, списываем, переводим и тд)
/// Сохраняем это в базу
/// Отправляем уведомление(например telegram)
