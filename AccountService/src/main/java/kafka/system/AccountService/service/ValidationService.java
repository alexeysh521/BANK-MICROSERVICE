package kafka.system.AccountService.service;


import kafka.system.AccountService.persistence.model.Account;
import kafka.system.AccountService.persistence.repository.AccountRepository;
import kafka.system.core.enums.AccountStatusType;
import kafka.system.core.enums.TransactionType;
import kafka.system.core.enums.UserStatusType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class ValidationService {

    private final AccountRepository accountRepository;

    public ValidationService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account executeAccount(UUID accountId){
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found " + accountId));
    }

    public void validateUserStatusForTransactionBegin(UserStatusType userStatus,
                                                      TransactionType typeTran,
                                                      AccountStatusType accountStatus){
        if(typeTran == TransactionType.TRANSFER && userStatus == UserStatusType.LIMITED_ACCESS)
            throw new IllegalArgumentException("To send money, you need to verify your details");
        else if(userStatus == UserStatusType.BLOCKED)
            throw new IllegalArgumentException("You are blocked and cannot send, withdraw or deposit money");
        else if(accountStatus == AccountStatusType.CLOSED)
            throw new IllegalArgumentException(
                    "You cannot debit, deposit, or transfer money because your account is closed"
            );
    }

    public void checkOwnerForAccount(UUID account_user_id, UUID user_id){
        if(!account_user_id.equals(user_id))
            throw new IllegalArgumentException("You are not the owner of this account");
    }

    public void checkBalanceV(BigDecimal balance, BigDecimal amount){
        if(balance.compareTo(amount) < 0)
            throw new IllegalArgumentException("There are not enough funds in your account");
    }

    public BigDecimal checkBalance(BigDecimal balance, BigDecimal amount){
        if(balance.compareTo(amount) < 0)
            throw new IllegalArgumentException("There are not enough funds in your account");
        return amount;
    }
}
