package kafka.system.Front_for_bank_microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class FrontForBankMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FrontForBankMicroserviceApplication.class, args);
	}

}
