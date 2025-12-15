package kafka.system.core.dto.AccountService;

import kafka.system.core.enums.CurrencyType;
import lombok.Data;
import java.math.BigDecimal;
import java.util.UUID;

@Data
public class CreateAcc {
    private UUID userId;
    private BigDecimal balance;
    private CurrencyType currency;
}
