package kafka.system.api_gateway_forBank.controller;

import kafka.system.core.dto.PeopleService.AddUserDataDto;
import kafka.system.core.dto.PeopleService.ViewDataUser;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
public class PeopleServiceController {

    private final WebClient.Builder webClientBuilder;

    public PeopleServiceController(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    @PostMapping("/users/addData")
    public Mono<String> addDataUser(@RequestBody AddUserDataDto request) {
        return webClientBuilder.build()
                .post()
                .uri("lb://PEOPLE-SERVICE/users/addData")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(String.class);
    }

    @PostMapping("/users/changeData")
    public Mono<String> changeDataUser(@RequestBody AddUserDataDto request) {
        return webClientBuilder.build()
                .post()
                .uri("lb://PEOPLE-SERVICE/users/changeData")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(String.class);
    }

    @GetMapping("/users/view/data/{userId}")
    public Mono<ViewDataUser> viewDataUser(@PathVariable UUID userId) {
        return webClientBuilder.build()
                .get()
                .uri("lb://PEOPLE-SERVICE/users/view/data/{userId}", userId)
                .retrieve()
                .bodyToMono(ViewDataUser.class);
    }
}
