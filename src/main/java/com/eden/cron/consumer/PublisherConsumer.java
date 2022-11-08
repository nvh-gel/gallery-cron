package com.eden.cron.consumer;

import com.eden.common.consumer.BaseConsumer;
import com.eden.common.utils.Action;
import com.eden.common.utils.QueueMessage;
import com.eden.cron.service.PublisherService;
import com.eden.cron.viewmodel.PublisherVM;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.function.UnaryOperator;

/**
 * Consumer for publisher.
 */
@Component
@Log4j2
public class PublisherConsumer extends BaseConsumer<PublisherVM> {

    private final PublisherService publisherService;

    /**
     * Constructor.
     *
     * @param publisherService publisher service bean
     */
    public PublisherConsumer(PublisherService publisherService) {

        this.publisherService = publisherService;

        this.actionMap.put(Action.CREATE, this.publisherService::create);
        this.actionMap.put(Action.UPDATE, this.publisherService::update);
        this.actionMap.put(Action.DELETE, x -> this.publisherService.delete(x.getId()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @KafkaListener(topics = "${cloudkarafka.topic.publisher}")
    public void processMessage(QueueMessage<PublisherVM> message) {

        log.info("received message {}", message);
        UnaryOperator<PublisherVM> function = actionMap.getOrDefault(message.getAction(), null);
        if (function != null) {
            PublisherVM result = function.apply(message.getMessage());
            log.info("processed {} publisher: {}", message.getAction(), result);
        }
    }
}
