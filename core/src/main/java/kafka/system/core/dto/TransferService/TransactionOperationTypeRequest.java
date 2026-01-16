package kafka.system.core.dto.TransferService;

import kafka.system.core.enums.TransactionType;
import lombok.Data;

@Data
public class TransactionOperationTypeRequest {
    private TransactionType transactionType;
}
