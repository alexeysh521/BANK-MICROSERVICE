package kafka.system.api_gateway_forBank.controller;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import kafka.system.core.dto.TransferService.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/transfer")
public class TransferServiceController {

    private final WebClient.Builder webClientBuilder;

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public TransferServiceController(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    @PostMapping("/deposit")
    public Mono<String> deposit(@RequestBody DepositRequest request) {
        LOGGER.info("deposit request: " + request);
        return webClientBuilder.build()
                .post()
                .uri("lb://TRANSFER-SERVICE/transfer/deposit")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(String.class);
    }

    @PostMapping("/withdraw")
    public Mono<String> withdraw(@RequestBody WithdrawRequest request) {
        return webClientBuilder.build()
                .post()
                .uri("lb://TRANSFER-SERVICE/transfer/withdraw")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(String.class);
    }

    @PostMapping("/transfers")
    public Mono<String> withdraw(@RequestBody TransferRequest request) {
        return webClientBuilder.build()
                .post()
                .uri("lb://TRANSFER-SERVICE/transfer/transfers")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(String.class);
    }

}
