package kafka.system.api_gateway_forBank.controller;

import kafka.system.core.dto.AccountService.AccountBalanceDto;
import kafka.system.core.dto.AccountService.CreateAcc;
import kafka.system.core.dto.TransferService.DepositRequest;
import kafka.system.core.dto.model.AccountMap;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/account")
public class AccountServiceController {

    private final WebClient.Builder webClientBuilder;

    public AccountServiceController(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    @GetMapping("/balance/{accId}")
    public Mono<AccountBalanceDto> balance(@PathVariable UUID accId) {

        return webClientBuilder.build()
                .get()
                .uri("lb://ACCOUNT-SERVICE/account/balance/{accId}", accId)
                .retrieve()
                .bodyToMono(AccountBalanceDto.class);
    }

    @GetMapping("/data/{accId}")
    public Mono<AccountMap> viewDataAboutAcc(@PathVariable UUID accId){

        return webClientBuilder.build()
                .get()
                .uri("lb://ACCOUNT-SERVICE/account/data/{accId}", accId)
                .retrieve()
                .bodyToMono(AccountMap.class);
    }

    @GetMapping("/myAccounts/{userId}")
    public Mono<List<AccountMap>> viewMyAccounts(@PathVariable UUID userId){

        return webClientBuilder.build()
                .get()
                .uri("lb://ACCOUNT-SERVICE/account/myAccounts/{userId}", userId)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<>() {});
    }

    @PostMapping("/create")
    public Mono<String> createAccount(@RequestBody CreateAcc createAcc) {

        return webClientBuilder.build()
                .post()
                .uri("lb://ACCOUNT-SERVICE/account/create")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(createAcc)
                .retrieve()
                .bodyToMono(String.class);
    }

}
