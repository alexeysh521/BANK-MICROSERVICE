package kafka.system.core.dto.model;

import kafka.system.core.enums.AccountStatusType;
import kafka.system.core.enums.CurrencyType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class AccountMap {
    private UUID accountId;

    private UUID userId;

    private BigDecimal balance = BigDecimal.ZERO;

    private CurrencyType currency;

    private AccountStatusType status = AccountStatusType.PENDING_VERIFICATION;

    private LocalDateTime timestamp;
}
