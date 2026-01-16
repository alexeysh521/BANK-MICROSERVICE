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

    // посмотреть свой баланс

    // посмотреть данные аккаунта

    // создать новый аккаунт
    @PostMapping("/create")
    public ResponseEntity<String> createAccount(@RequestBody CreateAcc createAcc){
        accountService.createAccount(createAcc);
        return ResponseEntity.ok("Create account success");
    }

    // посмотреть список моих аккаунтов
    @PostMapping("/myAccounts")
    public ResponseEntity<List<AccountMap>> viewMyAccounts(@RequestHeader(value = "X-User-Id", required = false)
                                                               String userId) {
        return ResponseEntity.ok(accountService.findAllByUserId(UUID.fromString(userId)));
    }
}
