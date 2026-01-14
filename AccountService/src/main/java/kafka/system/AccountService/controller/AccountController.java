package kafka.system.AccountService.controller;


import kafka.system.AccountService.service.AccountServiceImpl;
import kafka.system.core.dto.AccountService.AccountBalanceDto;
import kafka.system.core.dto.AccountService.CreateAcc;
import kafka.system.core.dto.AccountService.UuidIdRequest;
import kafka.system.core.dto.model.AccountMap;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {

    private final AccountServiceImpl accountService;

    @PostMapping("/balance")
    public ResponseEntity<AccountBalanceDto> viewBalance(@RequestBody UuidIdRequest request) {
        return ResponseEntity.ok(accountService.viewBalance(request.getId()));
    }

    @PostMapping("/data")
    public ResponseEntity<AccountMap> viewDataAboutAcc(@RequestBody UuidIdRequest request) {
        return ResponseEntity.ok(accountService.findAllOnAcc(request.getId()));
    }

    @PostMapping("/create")
    public ResponseEntity<String> createAccount(@RequestBody CreateAcc createAcc){
        accountService.createAccount(createAcc);
        return ResponseEntity.ok("Create account success");
    }

    @PostMapping("/myAccounts")
    public ResponseEntity<List<AccountMap>> viewMyAccounts(@RequestBody UuidIdRequest request) {
        return ResponseEntity.ok(accountService.findAllByUserId(request.getId()));
    }
}
