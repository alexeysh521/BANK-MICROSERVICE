package kafka.system.TransferService.controller;

import kafka.system.TransferService.persistence.repository.TransferRepository;
import kafka.system.TransferService.service.TransferServiceImpl;
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
public class TransferController {

    private final TransferServiceImpl transferService;
    private final TransferRepository transferRepository;
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @PostMapping("/deposit")
    public ResponseEntity<?> deposit(@RequestHeader(value = "X-User-Id", required = false) String userId,
                                     @RequestBody DepositRequest depositRequest) {
        transferService.operation(depositRequest, userId);
        return ResponseEntity.ok("Deposit request sent successfully");
    }

    @PostMapping("/withdraw")
    public ResponseEntity<?> withdraw(@RequestHeader(value = "X-User-Id", required = false) String userId,
            @RequestBody WithdrawRequest withdrawRequest) {
        transferService.operation(withdrawRequest, userId);
        return ResponseEntity.ok("Withdraw request sent successfully");
    }

    @PostMapping("/transfers")
    public ResponseEntity<?> transfer(@RequestHeader(value = "X-User-Id", required = false) String userId,
            @RequestBody TransferRequest transferRequest) {
        UUID id = transferService.operation(transferRequest, userId);
        return ResponseEntity.ok("Transfer request sent successfully with id: " + id);
    }

    @PostMapping("/getStatusTran")
    public ResponseEntity<?> requestStatus(@RequestBody UuidIdRequest request){
        TransactionStatusType responseStatus = transferRepository.findTransactionById(request.getId()).orElseThrow(
                () -> new TransfersServiceException("Transaction with id not found"));
        return ResponseEntity.ok(responseStatus);
    }

    //посмотреть список транзакций
    @GetMapping("/list")
    public ResponseEntity<?> transactionList(){
        return ResponseEntity.ok(transferRepository.findAll());
    }
}
