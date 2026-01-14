package kafka.system.TransferService.controller;


import kafka.system.TransferService.persistence.repository.TransferRepository;
import kafka.system.TransferService.service.TransferServiceImpl;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transfer/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final TransferServiceImpl transferService;
    private final TransferRepository transferRepository;
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    //посмотреть список транзакций
    @GetMapping("/list")
    public ResponseEntity<?> transactionList(){
        return ResponseEntity.ok(transferRepository.findAll());
    }

    //просмотр транзакции по ее id

    //просмотр транзакций по id пользователя

    //просмотр транзакции по id аккаунта

    //сортировка по времени создания транзакции

    //получить транзакции по статусу транзакции (SUCCESS, FAILED, PENDING)

    //сортировка по виду операции в транзакции (DEPOSIT, TRANSFER, WITHDRAW)

    //просмотр транзакций за время (вводится: )


}
