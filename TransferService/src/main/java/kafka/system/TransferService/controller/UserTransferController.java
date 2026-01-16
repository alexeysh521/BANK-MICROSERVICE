package kafka.system.TransferService.controller;

import kafka.system.TransferService.persistence.repository.TransactionRepository;
import kafka.system.TransferService.service.TransactionServiceImpl;
import kafka.system.core.dto.AccountService.UuidIdRequest;
import kafka.system.core.dto.TransferService.*;
import kafka.system.core.enums.TransactionStatusType;
import kafka.system.core.exception.TransfersServiceException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequiredArgsConstructor
@RequestMapping("/transfer")
public class UserTransferController {

    private final TransactionServiceImpl transactionService;
    private final TransactionRepository transactionRepository;
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    // пополнить счет
    @PostMapping("/deposit")
    public ResponseEntity<?> deposit(@RequestHeader(value = "X-User-Id", required = false) String userId,
                                     @RequestBody DepositRequest depositRequest) {
        transactionService.operation(depositRequest, userId);
        return ResponseEntity.ok("Deposit request sent successfully");
    }

    // снять деньги
    @PostMapping("/withdraw")
    public ResponseEntity<?> withdraw(@RequestHeader(value = "X-User-Id", required = false) String userId,
            @RequestBody WithdrawRequest withdrawRequest) {
        transactionService.operation(withdrawRequest, userId);
        return ResponseEntity.ok("Withdraw request sent successfully");
    }

    // перевести деньги
    @PostMapping("/transfers")
    public ResponseEntity<?> transfer(@RequestHeader(value = "X-User-Id", required = false) String userId,
            @RequestBody TransferRequest transferRequest) {
        UUID id = transactionService.operation(transferRequest, userId);
        return ResponseEntity.ok("Transfer request sent successfully with id: " + id);
    }

    // посмотреть статус транзакции по id
    @PostMapping("/getStatusTran")
    public ResponseEntity<?> requestStatus(@RequestBody UuidIdRequest request){
        TransactionStatusType responseStatus = transactionRepository.findTransactionById(request.getId()).orElseThrow(
                () -> new TransfersServiceException("Transaction with id not found"));
        return ResponseEntity.ok(responseStatus);
    }


    // посмотреть список своих транзакций (проверить безопасность)
    @GetMapping("/viewTransactionsByUserId/{userId}")
    public ResponseEntity<?> viewTransactionByUserId(@PathVariable UUID userId) {
        return ResponseEntity.ok(transactionService.viewTransactionsByUserId(userId));
    }

}
