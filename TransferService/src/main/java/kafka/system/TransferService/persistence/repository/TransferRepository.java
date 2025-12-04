package kafka.system.TransferService.persistence.repository;

import kafka.system.TransferService.persistence.model.Transaction;
import kafka.system.core.enums.TransactionStatusType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TransferRepository extends JpaRepository<Transaction, UUID> {

    @Query("SELECT t.transactionStatus FROM Transaction t WHERE t.transferId = :tranId")
    Optional<TransactionStatusType> findTransactionById(@Param("tranId") UUID tranId);

}
