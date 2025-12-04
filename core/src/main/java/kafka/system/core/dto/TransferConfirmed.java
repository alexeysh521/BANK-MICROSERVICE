package kafka.system.core.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Deprecated
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransferConfirmed {
    private UUID transactionId;
    private LocalDateTime timestamp;
    private UUID accountId;
    private BigDecimal newBalance;
}


