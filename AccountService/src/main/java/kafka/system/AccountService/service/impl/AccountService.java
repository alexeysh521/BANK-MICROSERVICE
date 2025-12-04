package kafka.system.AccountService.service.impl;



import kafka.system.core.dto.TransferService.*;
import kafka.system.core.enums.CurrencyType;

import java.util.UUID;

public interface AccountService {

    void deposit(DepositRequest dto);

    void withdraw(WithdrawRequest dto);

    void transfer(TransferRequest dto);

//    void createAccount(CreateDefaultAcc dto);
}
