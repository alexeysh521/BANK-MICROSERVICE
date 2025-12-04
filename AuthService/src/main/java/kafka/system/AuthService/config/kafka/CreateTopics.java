package kafka.system.AuthService.config.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class CreateTopics {

    @Bean
    NewTopic createAdminTopic(){
        return TopicBuilder
                .name("create-admin-events-topic")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    NewTopic createManagerTopic(){
        return TopicBuilder
                .name("create-manager-events-topic")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    NewTopic createUserTopic(){
        return TopicBuilder
                .name("create-user-events-topic")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    NewTopic createDefaultAccountEventTopic(){
        return TopicBuilder
                .name("create-default-account-event-topic")
                .partitions(1)
                .replicas(1)
                .build();
    }

}
