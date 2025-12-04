package kafka.system.api_gateway_forBank.controller;

import kafka.system.core.dto.AuthService.AdminRegisterRequest;
import kafka.system.core.dto.AuthService.ManagerRegisterRequest;
import kafka.system.core.dto.AuthService.UserRegisterRequest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthServiceController {

    private final WebClient.Builder webClientBuilder;

    public AuthServiceController(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    @PostMapping("/register/admin")
    public Mono<Map<String, String>> registerAdmin(@RequestBody AdminRegisterRequest request) {
        return webClientBuilder.build()
                .post()
                .uri("lb://AUTH-SERVICE/auth/register/admin")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse ->
                        clientResponse.bodyToMono(String.class)
                                .flatMap(msg -> Mono.error(new IllegalArgumentException(msg)))
                )
                .bodyToMono(new ParameterizedTypeReference<>() {});
    }

    @PostMapping("/login/admin")
    public Mono<Map<String, String>> loginAdmin(@RequestBody AdminRegisterRequest request) {
        return webClientBuilder.build()
                .post()
                .uri("lb://AUTH-SERVICE/auth/login/admin")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse ->
                        clientResponse.bodyToMono(String.class)
                                .flatMap(msg -> Mono.error(new IllegalArgumentException(msg)))
                )
                .bodyToMono(new ParameterizedTypeReference<>() {});
    }

    /// ------------------------------------------------------------------------------------------------///

    @PostMapping("/register/manager")
    public Mono<Map<String, String>> registerManager(@RequestBody ManagerRegisterRequest request) {
        return webClientBuilder.build()
                .post()
                .uri("lb://AUTH-SERVICE/auth/register/manager")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse ->
                        clientResponse.bodyToMono(String.class)
                                .flatMap(msg -> Mono.error(new IllegalArgumentException(msg)))
                )
                .bodyToMono(new ParameterizedTypeReference<>() {});
    }

    @PostMapping("/login/manager")
    public Mono<Map<String, String>> loginManager(@RequestBody ManagerRegisterRequest request) {
        return webClientBuilder.build()
                .post()
                .uri("lb://AUTH-SERVICE/auth/login/manager")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse ->
                        clientResponse.bodyToMono(String.class)
                                .flatMap(msg -> Mono.error(new IllegalArgumentException(msg)))
                )
                .bodyToMono(new ParameterizedTypeReference<>() {});
    }

    /// ------------------------------------------------------------------------------------------------///

    @PostMapping("/register/user")
    public Mono<Map<String, String>> registerUser(@RequestBody UserRegisterRequest request) {
        return webClientBuilder.build()
                .post()
                .uri("lb://AUTH-SERVICE/auth/register/user")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse ->
                        clientResponse.bodyToMono(String.class)
                                .flatMap(msg -> Mono.error(new IllegalArgumentException(msg)))
                )
                .bodyToMono(new ParameterizedTypeReference<>() {});
    }

    @PostMapping("/login/user")
    public Mono<Map<String, String>> loginUser(@RequestBody UserRegisterRequest request) {
        return webClientBuilder.build()
                .post()
                .uri("lb://AUTH-SERVICE/auth/login/user")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse ->
                        clientResponse.bodyToMono(String.class)
                                .flatMap(msg -> Mono.error(new IllegalArgumentException(msg)))
                )
                .bodyToMono(new ParameterizedTypeReference<>() {});
    }

}

/// дополнительные данные про админов и манаджеров, удалить
