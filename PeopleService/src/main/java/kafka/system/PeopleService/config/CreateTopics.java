package kafka.system.PeopleService.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class CreateTopics {

    @Bean
    NewTopic createViewAccTopic(){
        return TopicBuilder
                .name("view-accounts-events-topic")
                .partitions(1)
                .replicas(1)
                .build();
    }



}
