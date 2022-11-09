package com.eden.cron.producer;

import com.eden.common.producer.BaseProducer;
import com.eden.common.utils.QueueMessage;
import com.eden.cron.utils.Constants;
import com.eden.cron.viewmodel.PublisherVM;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * Producer for publisher.
 */
@Component
@Log4j2
public class PublisherProducer implements BaseProducer<PublisherVM> {

    private KafkaTemplate<String, QueueMessage<PublisherVM>> kafkaTemplate;

    @Value("${cloudkarafka.topic.publisher}")
    private String topic;

    /**
     * {@inheritDoc}
     */
    @Override
    public void send(QueueMessage<PublisherVM> message) {

        this.kafkaTemplate.send(topic, message);
        log.info(Constants.SENT_MESSAGE, message, topic);
    }

    /**
     * Setter.
     */
    @Autowired
    public void setKafkaTemplate(KafkaTemplate<String, QueueMessage<PublisherVM>> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
}
