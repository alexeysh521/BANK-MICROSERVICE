package kafka.system.core.dto.TransferService;

import kafka.system.core.enums.TransactionStatusType;
import lombok.Data;

@Data
public class TransactionStatusRequest {
    private TransactionStatusType transactionStatusType;
}
