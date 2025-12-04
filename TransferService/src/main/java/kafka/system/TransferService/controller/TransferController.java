package kafka.system.TransferService.controller;

import kafka.system.TransferService.persistence.repository.TransferRepository;
import kafka.system.TransferService.service.TransferServiceImpl;
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
public class TransferController {

    private final TransferServiceImpl transferService;
    private final TransferRepository transferRepository;
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @PostMapping("/deposit")
    public ResponseEntity<?> deposit(@RequestHeader(value = "X-Test", required = false) String userId,
                                     @RequestBody DepositRequest depositRequest) {
        LOGGER.info("❌{}", userId);
        transferService.operation(depositRequest);
        return ResponseEntity.ok("Deposit request sent successfully");
    }

    @PostMapping("/withdraw")
    public ResponseEntity<?> withdraw(@RequestBody WithdrawRequest withdrawRequest) {
        transferService.operation(withdrawRequest);
        return ResponseEntity.ok("Withdraw request sent successfully");
    }

    @PostMapping("/transfers")
    public ResponseEntity<?> transfer(@RequestBody TransferRequest transferRequest) {
        UUID id = transferService.operation(transferRequest);
        return ResponseEntity.ok("Transfer request sent successfully with id: " + id);
    }

    @GetMapping("/getStatusTran/{id}")
    public ResponseEntity<?> requestStatus(@PathVariable UUID id){
        TransactionStatusType responseStatus = transferRepository.findTransactionById(id).orElseThrow(
                () -> new TransfersServiceException("Transaction with id not found"));
        return ResponseEntity.ok(responseStatus);
    }

    //посмотреть список транзакций
    @GetMapping("/list")
    public ResponseEntity<?> transactionList(){
        return ResponseEntity.ok(transferRepository.findAll());
    }
}
