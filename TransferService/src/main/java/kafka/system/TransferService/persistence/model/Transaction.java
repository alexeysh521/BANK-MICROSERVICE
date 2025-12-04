package kafka.system.TransferService.persistence.model;

import jakarta.persistence.*;
import kafka.system.core.enums.CurrencyType;
import kafka.system.core.enums.TransactionStatusType;
import kafka.system.core.enums.TransactionType;
import lombok.Getter;
import lombok.Setter;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID transferId;

    @Column(nullable = false)
    private UUID userId;

    @Column(nullable = false)
    private UUID fromAccId;

    private UUID toAccId;

    @Column(precision = 9, scale = 2, nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CurrencyType currency;

    @Column(name = "transaction_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @Column(name = "transaction_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionStatusType transactionStatus;

    @Column(name = "date", nullable = false)
    private LocalDateTime timestamp;

    public Transaction() {}

    public Transaction(UUID userId, UUID fromAccId, UUID toAccId, BigDecimal amount, CurrencyType currency,
                       TransactionType transactionType, TransactionStatusType transactionStatus, LocalDateTime timestamp) {
        this.userId = userId;
        this.fromAccId = fromAccId;
        this.toAccId = toAccId;
        this.amount = amount;
        this.currency = currency;
        this.transactionType = transactionType;
        this.transactionStatus = transactionStatus;
        this.timestamp = timestamp;
    }

}
