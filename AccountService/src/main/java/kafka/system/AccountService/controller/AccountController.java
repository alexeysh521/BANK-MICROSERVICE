package kafka.system.AccountService.controller;


import kafka.system.AccountService.service.AccountServiceImpl;
import kafka.system.core.dto.AccountService.AccountBalanceDto;
import kafka.system.core.dto.AccountService.CreateAcc;
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

    @GetMapping("/balance/{accId}")
    public ResponseEntity<AccountBalanceDto> viewBalance(@PathVariable UUID accId) {
        return ResponseEntity.ok(accountService.viewBalance(accId));
    }

    @GetMapping("/data/{accId}")
    public ResponseEntity<AccountMap> viewDataAboutAcc(@PathVariable UUID accId) {
        return ResponseEntity.ok(accountService.findAllOnAcc(accId));
    }

    @PostMapping("/create")
    public ResponseEntity<String> createAccount(@RequestBody CreateAcc createAcc){
        accountService.createAccount(createAcc);
        return ResponseEntity.ok("Create account success");
    }

    @GetMapping("/myAccounts/{userId}")
    public ResponseEntity<List<AccountMap>> viewMyAccounts(@PathVariable UUID userId) {
        return ResponseEntity.ok(accountService.findAllByUserId(userId));
    }
}
