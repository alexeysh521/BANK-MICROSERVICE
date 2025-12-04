package kafka.system.api_gateway_forBank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ApiGatewayForBankApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayForBankApplication.class, args);
	}

}
