package com.eden.cron.producer;

import com.eden.common.utils.QueueMessage;
import com.eden.cron.viewmodel.ModelVM;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * Message producer for Model
 */
@Component
@Log4j2
public class ModelProducer {

    private final KafkaTemplate<String, QueueMessage<ModelVM>> kafkaTemplate;

    @Value("${cloudkarafka.topic}")
    private String topic;

    /**
     * Constructor.
     *
     * @param kafkaTemplate kafka template bean
     */
    public ModelProducer(KafkaTemplate<String, QueueMessage<ModelVM>> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * Send a message to kafka topic.
     *
     * @param message processing message
     */
    public void send(QueueMessage<ModelVM> message) {

        this.kafkaTemplate.send(topic, message);
        log.info("sent message {} to topic {}", message, topic);
    }
}
