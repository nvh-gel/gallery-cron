package com.eden.cron.producer;

import com.eden.common.utils.QueueMessage;
import com.eden.cron.viewmodel.ModelVM;
import com.eden.cron.viewmodel.NicknameVM;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * Message producer for Model
 */
@Component
@Log4j2
public class ModelProducer {

    private KafkaTemplate<String, QueueMessage<ModelVM>> modelKafkaTemplate;

    private KafkaTemplate<String, QueueMessage<NicknameVM>> nickKafkaTemplate;

    @Value("${cloudkarafka.topic.model}")
    private String modelTopic;

    @Value("${cloudkarafka.topic.nick}")
    private String nickTopic;

    /**
     * Send a message to kafka topic.
     *
     * @param message processing message
     */
    public void send(QueueMessage<ModelVM> message) {

        this.modelKafkaTemplate.send(modelTopic, message);
        log.info("sent message {} to topic {}", message, modelTopic);
    }

    /**
     * Send nick message to kafka topic.
     *
     * @param message message to send
     */
    public void sendNick(QueueMessage<NicknameVM> message) {

        this.nickKafkaTemplate.send(nickTopic, message);
        log.info("sent message {} to topic {}", message, nickTopic);
    }

    /**
     * Setter.
     */
    @Autowired
    public void setModelKafkaTemplate(KafkaTemplate<String, QueueMessage<ModelVM>> modelKafkaTemplate) {
        this.modelKafkaTemplate = modelKafkaTemplate;
    }

    /**
     * Setter.
     */
    @Autowired
    public void setNickKafkaTemplate(KafkaTemplate<String, QueueMessage<NicknameVM>> nickKafkaTemplate) {
        this.nickKafkaTemplate = nickKafkaTemplate;
    }
}
