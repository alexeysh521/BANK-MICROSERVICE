package kafka.system.core.dto.TransferService;

import kafka.system.core.enums.CurrencyType;
import kafka.system.core.enums.TransactionStatusType;
import kafka.system.core.enums.TransactionType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class TransactionConvert {
    private UUID transferId;
    private UUID userId;
    private UUID fromAccId;
    private UUID toAccId;
    private BigDecimal amount;
    private CurrencyType currency;
    private TransactionType transactionType;
    private TransactionStatusType transactionStatus;
    private LocalDateTime timestamp;
}
