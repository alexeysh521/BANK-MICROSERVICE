package kafka.system.TransferService.controller;

import kafka.system.TransferService.service.TransactionServiceImpl;
import kafka.system.core.dto.TransferService.TransactionOperationTypeRequest;
import kafka.system.core.dto.TransferService.TransactionStatusRequest;
import kafka.system.core.enums.TransactionPeriod;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transfer/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final TransactionServiceImpl transactionService;
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    // посмотреть список транзакций
    @GetMapping("/list")
    public ResponseEntity<?> transactionList(){
        return ResponseEntity.ok(transactionService.getTransactionList());
    }

    // просмотр транзакции по ее id
    @GetMapping("/viewTransactionById/{tranId}")
    public ResponseEntity<?> viewTransactionById(@PathVariable UUID tranId){
        return ResponseEntity.ok(transactionService.viewTransactionByTranId(tranId));
    }

    // просмотр транзакций по id пользователя (проверить безопасность)
    @GetMapping("/viewTransactionsByUserId/{userId}")
    public ResponseEntity<?> viewTransactionByUserId(@PathVariable UUID userId) {
        return ResponseEntity.ok(transactionService.viewTransactionsByUserId(userId));
    }

    // по id аккаунта (проверить безопасность)
    @GetMapping("/viewTransactionsByAccountId/{accountId}")
    public ResponseEntity<?> viewTransactionByAccountId(@PathVariable UUID accountId) {
        return ResponseEntity.ok(transactionService.viewTransactionsByAccountId(accountId));
    }

    // сортировка по времени создания транзакции (самые новые)
    @GetMapping("/viewTransactionsFromNewToOld")
    public ResponseEntity<?> viewTransactionFromNewToOld(){
        return ResponseEntity.ok(transactionService.getTransactionFromNewToOld());
    }

    // сортировка по времени создания транзакции (самые старые)
    @GetMapping("/viewTransactionsFromOldToNew")
    public ResponseEntity<?> viewTransactionFromOldToNew(){
        return ResponseEntity.ok(transactionService.getTransactionFromOldToNew());
    }

    // получить сумму всех переводов пользователя по его id (проверить безопасность)
    @GetMapping("/viewAmountUserTransfers/{userId}")
    public ResponseEntity<BigDecimal> getAmountByUserId(@PathVariable UUID userId){
        return ResponseEntity.ok(transactionService.getAmountUser(userId));
    }

    // получить транзакции по статусу транзакции (SUCCESS, FAILED, PENDING)
    @PostMapping("/viewTransactionsByTranStatus")
    public ResponseEntity<?> getTransactionByStatusType(@RequestBody TransactionStatusRequest type){
        return ResponseEntity.ok(transactionService.getTransactionByTransactionStatus(type.getTransactionStatusType()));
    }

    // сортировка по виду операции в транзакции (DEPOSIT, TRANSFER, WITHDRAW)
    @PostMapping("/viewTransactionsByOperationType")
    public ResponseEntity<?> getTransactionByOperationType(@RequestBody TransactionOperationTypeRequest type){
        return ResponseEntity.ok(transactionService.getTransactionByOperationStatus(type.getTransactionType()));
    }

    // просмотр транзакций за время (час, день, неделя, месяц, год)
    @PostMapping("/viewTransactionOverTime")
    public ResponseEntity<?> viewTransactionOverTime(@RequestBody TransactionPeriod period){
        return ResponseEntity.ok(transactionService.getTransactionOverTime(period));
    }

}
