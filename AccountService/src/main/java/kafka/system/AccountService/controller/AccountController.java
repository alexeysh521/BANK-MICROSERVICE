package kafka.system.AccountService.controller;


import kafka.system.AccountService.service.AccountServiceImpl;
import kafka.system.core.dto.model.AccountMap;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {

    private final AccountServiceImpl accountService;

    @GetMapping("/viewBalance/{accId}")
    public ResponseEntity<AccountMap> viewBalance(@PathVariable UUID accId) {
        return ResponseEntity.ok(accountService.findAllOnAcc(accId));
    }

    /// создать счет
}
