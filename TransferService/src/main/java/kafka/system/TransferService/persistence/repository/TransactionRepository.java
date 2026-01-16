package kafka.system.TransferService.persistence.repository;

import kafka.system.TransferService.persistence.model.Transaction;
import kafka.system.core.enums.TransactionStatusType;
import kafka.system.core.enums.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    @Query("SELECT t.transactionStatus FROM Transaction t WHERE t.transferId = :tranId")
    Optional<TransactionStatusType> findTransactionById(@Param("tranId") UUID tranId);

    List<Transaction> findTransactionByUserId(UUID userId);

    List<Transaction> findTransactionByFromAccId(UUID fromAccId);

    List<Transaction> findAllByOrderByTimestampAsc();

    List<Transaction> findAllByOrderByTimestampDesc();

    @Query("SELECT t FROM Transaction t WHERE t.transactionStatus = :status")
    List<Transaction> findTransactionByTransactionStatus(@Param("status") TransactionStatusType transactionStatus);

    @Query("SELECT t FROM Transaction t WHERE t.transactionType = :status")
    List<Transaction> findTransactionByTransactionType(@Param("status") TransactionType transactionType);
}
