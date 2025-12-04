package kafka.system.core.dto.TransferService;

import kafka.system.core.enums.CurrencyType;
import kafka.system.core.enums.UserStatusType;
import kafka.system.core.interfaces.TransactionBase;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class TransferRequest implements TransactionBase {
    private UUID userId;
    private UUID fromAccId;
    private UUID toAccId;
    private BigDecimal amount;
    private CurrencyType currencyType;
    private UserStatusType userStatus;
    private UUID transactionId;
}
