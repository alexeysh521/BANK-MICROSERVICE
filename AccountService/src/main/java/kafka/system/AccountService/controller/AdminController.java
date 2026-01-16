package kafka.system.AccountService.controller;

import kafka.system.AccountService.service.AccountServiceImpl;
import kafka.system.core.dto.AccountService.AccountBalanceDto;
import kafka.system.core.dto.AccountService.UuidIdRequest;
import kafka.system.core.dto.model.AccountMap;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/account/admin")
public class AdminController {

    private final AccountServiceImpl accountService;

    // посмотреть баланс аккаунта пользователя
    @PostMapping("/balance")
    public ResponseEntity<AccountBalanceDto> viewBalance(@RequestBody UuidIdRequest request) {
        return ResponseEntity.ok(accountService.viewBalance(request.getId()));
    }

    // просмотр данных аккаунта пользователя
    @PostMapping("/data")
    public ResponseEntity<AccountMap> viewDataAboutAcc(@RequestBody UuidIdRequest request) {
        return ResponseEntity.ok(accountService.findAllOnAcc(request.getId()));
    }

    // посмотреть список аккаунтов пользователя

    // заблокировать, удалить, разблокировать аккаунт пользователя (3 метода)

    // поменять статус аккаунта пользователя

    // создать пользователю аккаунт



}
