package kafka.system.api_gateway_forBank.controller;

import kafka.system.core.dto.model.AccountMap;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/account")
public class AccountServiceController {

    private final WebClient.Builder webClientBuilder;

    public AccountServiceController(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    @GetMapping("/viewBalance/{accId}")
    public Mono<AccountMap> getUser(@PathVariable UUID accId) {

        return webClientBuilder.build()
                .get()
                .uri("lb://ACCOUNT-SERVICE/account/viewBalance/{accId}", accId)
                .retrieve()
                .bodyToMono(AccountMap.class);
    }
}
