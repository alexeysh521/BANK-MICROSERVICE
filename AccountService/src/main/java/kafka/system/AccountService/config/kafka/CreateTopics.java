package kafka.system.AccountService.config.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class CreateTopics {

    @Bean
    NewTopic createConfirmTopic(){
        return TopicBuilder
                .name("transfer-confirmed-topic")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    NewTopic createFailureTopic(){
        return TopicBuilder
                .name("transfer-failure-topic")
                .partitions(1)
                .replicas(1)
                .build();
    }
}
