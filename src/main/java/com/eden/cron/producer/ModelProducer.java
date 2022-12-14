package com.eden.cron.producer;

import com.eden.common.producer.BaseProducer;
import com.eden.common.utils.QueueMessage;
import com.eden.cron.utils.Constants;
import com.eden.cron.viewmodel.ModelVM;
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
public class ModelProducer implements BaseProducer<ModelVM> {

    private KafkaTemplate<String, QueueMessage<ModelVM>> kafkaTemplate;

    @Value("${cloudkarafka.topic.model}")
    private String topic;

    /**
     * {@inheritDoc}
     */
    @Override
    public void send(QueueMessage<ModelVM> message) {

        this.kafkaTemplate.send(topic, message);
        log.info(Constants.SENT_MESSAGE, message, topic);
    }

    /**
     * Setter.
     */
    @Autowired
    public void setKafkaTemplate(KafkaTemplate<String, QueueMessage<ModelVM>> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
}
