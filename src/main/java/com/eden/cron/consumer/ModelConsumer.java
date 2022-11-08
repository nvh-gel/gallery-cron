package com.eden.cron.consumer;

import com.eden.common.consumer.BaseConsumer;
import com.eden.common.utils.Action;
import com.eden.common.utils.QueueMessage;
import com.eden.cron.service.ModelService;
import com.eden.cron.viewmodel.ModelVM;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.function.UnaryOperator;

/**
 * Message consumer for model.
 */
@Component
@Log4j2
public class ModelConsumer extends BaseConsumer<ModelVM> {

    private final ModelService modelService;

    /**
     * Constructor.
     *
     * @param modelService model service bean
     */
    public ModelConsumer(ModelService modelService) {

        this.modelService = modelService;

        this.actionMap.put(Action.CREATE, this.modelService::create);
        this.actionMap.put(Action.UPDATE, this.modelService::update);
        this.actionMap.put(Action.DELETE, x -> this.modelService.delete(x.getId()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @KafkaListener(topics = "${cloudkarafka.topic.model}")
    public void processMessage(QueueMessage<ModelVM> message) {

        log.info("message received: {}", message);
        UnaryOperator<ModelVM> function = actionMap.getOrDefault(message.getAction(), null);
        if (function != null) {
            ModelVM processed = function.apply(message.getMessage());
            log.info("processed {} model: {}", message.getAction(), processed);
        }
    }
}
