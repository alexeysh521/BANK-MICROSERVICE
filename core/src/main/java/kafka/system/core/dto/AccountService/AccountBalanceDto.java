package kafka.system.core.dto.AccountService;

import kafka.system.core.enums.CurrencyType;

import java.math.BigDecimal;


public record AccountBalanceDto(
        BigDecimal balance, CurrencyType currency
) {}
