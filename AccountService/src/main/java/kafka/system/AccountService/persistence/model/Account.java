package kafka.system.AccountService.persistence.model;

import jakarta.persistence.*;
import kafka.system.core.enums.AccountStatusType;
import kafka.system.core.enums.CurrencyType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID accountId;

    @Column(nullable = false)
    private UUID userId;

    private BigDecimal balance = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    private CurrencyType currency = CurrencyType.RUB;

    @Enumerated(EnumType.STRING)
    private AccountStatusType status = AccountStatusType.PENDING_VERIFICATION;

    private LocalDateTime timeStamp = LocalDateTime.now();

    public Account() {}

    public Account(UUID userId) {
        this.userId = userId;
    }

    public Account(UUID userId, BigDecimal balance, CurrencyType currency) {
        this.userId = userId;
        this.balance = balance;
        this.currency = currency;
    }

}
