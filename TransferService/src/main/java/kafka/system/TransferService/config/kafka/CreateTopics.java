package kafka.system.TransferService.config.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class CreateTopics {

    @Bean
    NewTopic createDepositTopic(){
        return TopicBuilder
                .name("deposit-events-topic")
                .partitions(3)
                .replicas(3)
                .build();
    }

    @Bean
    NewTopic createTransferTopic(){
        return TopicBuilder
                .name("transfer-events-topic")
                .partitions(3)
                .replicas(3)
                .build();
    }

    @Bean
    NewTopic createWithdrawTopic(){
        return TopicBuilder
                .name("withdraw-events-topic")
                .partitions(3)
                .replicas(3)
                .build();
    }
}
