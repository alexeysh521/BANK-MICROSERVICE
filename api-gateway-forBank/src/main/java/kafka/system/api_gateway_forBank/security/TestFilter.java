package kafka.system.api_gateway_forBank.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;


@Component
public class TestFilter extends AbstractGatewayFilterFactory<TestFilter.Config> {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public TestFilter() {
        super(Config.class);
        LOGGER.info("🔥 TestFilter constructor called!");
    }

    public static class Config {

    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            LOGGER.info("🔥🔥🔥 TestFilter ВЫЗВАН!");
            System.out.println("Path: " + exchange.getRequest().getURI().getPath());
            return chain.filter(exchange);
        };
    }
}