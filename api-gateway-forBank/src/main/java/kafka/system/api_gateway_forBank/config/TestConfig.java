package kafka.system.api_gateway_forBank.config;

import kafka.system.api_gateway_forBank.security.TestFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfig {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Bean
    public RouteLocator testRoute(RouteLocatorBuilder builder, TestFilter testFilter) {
        LOGGER.info("=== TestConfig.testRoute() called ===");
        LOGGER.info("SimpleTestFilter injected: " + (testFilter != null));

        return builder.routes()
                .route("test-route", r -> r
                        .path("/test/**")
                        .filters(f -> f
                                .filter(testFilter.apply(new TestFilter.Config()))
                        )
                        .uri("http://httpbin.org:80"))
                .build();
    }
}