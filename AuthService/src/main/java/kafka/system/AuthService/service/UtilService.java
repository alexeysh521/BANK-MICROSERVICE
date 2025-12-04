package kafka.system.AuthService.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UtilService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${create-default-account-event-topic}")
    private String createDefaultAccTopic;

    public UtilService(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void postCreateDefaultAcc(UUID userId) {
        kafkaTemplate.send(createDefaultAccTopic, userId)
                .whenComplete((result, exception) -> {
                    if (exception == null) {
                        LOGGER.info("Message sent successfully to Kafka topic '{}'", createDefaultAccTopic);
                    } else {
                        LOGGER.error("Failed to send message to Kafka topic '{}'", createDefaultAccTopic, exception);
                    }
                });
    }
}
