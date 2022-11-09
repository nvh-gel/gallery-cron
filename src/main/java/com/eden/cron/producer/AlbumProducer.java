package com.eden.cron.producer;

import com.eden.common.producer.BaseProducer;
import com.eden.common.utils.QueueMessage;
import com.eden.cron.utils.Constants;
import com.eden.cron.viewmodel.AlbumVM;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * Kafka producer for album.
 */
@Component
@Log4j2
public class AlbumProducer implements BaseProducer<AlbumVM> {

    private KafkaTemplate<String, QueueMessage<AlbumVM>> kafkaTemplate;

    @Value("${cloudkarafka.topic.album}")
    private String topic;

    /**
     * {@inheritDoc}
     */
    @Override
    public void send(QueueMessage<AlbumVM> queueMessage) {

        this.kafkaTemplate.send(topic, queueMessage);
        log.info(Constants.SENT_MESSAGE, queueMessage, topic);
    }

    /**
     * Setter.
     */
    @Autowired
    public void setKafkaTemplate(KafkaTemplate<String, QueueMessage<AlbumVM>> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
}
