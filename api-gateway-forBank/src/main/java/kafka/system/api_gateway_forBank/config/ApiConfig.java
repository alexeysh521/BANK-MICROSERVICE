package kafka.system.api_gateway_forBank.config;

import kafka.system.api_gateway_forBank.security.JwtAuthFilter;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ApiConfig {

    @Bean
    @LoadBalanced
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder,
                                           JwtAuthFilter jwtAuthFilter) {
        System.out.println("🔥 Building transfer-service route with JwtAuthFilter");

        return builder.routes()
                .route("transfer-service", r -> r
                        .path("/transfer/**")
                        .filters(f -> {
                            System.out.println("✅ Adding JwtAuthFilter to /transfer/**");
                            return f.filter(jwtAuthFilter.apply(new JwtAuthFilter.Config()));
                        })
                        .uri("lb://TRANSFER-SERVICE"))
                .build();
    }
}