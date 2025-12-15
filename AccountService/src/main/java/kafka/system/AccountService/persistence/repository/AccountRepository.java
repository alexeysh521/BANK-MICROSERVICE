package kafka.system.AccountService.persistence.repository;

import kafka.system.AccountService.persistence.model.Account;
import kafka.system.core.dto.AccountService.AccountBalanceDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {

    Optional<Account> findByUserId(UUID userId);

    List<Account> findAccountByUserId(UUID userId);

    @Query("select new kafka.system.core.dto.AccountService.AccountBalanceDto(a.balance, a.currency)" +
            "from Account a where a.accountId = :accId")
    AccountBalanceDto viewAccBalance(@Param("accId") UUID accId);

}
