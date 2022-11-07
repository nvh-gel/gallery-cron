package com.eden.cron.consumer;

import com.eden.common.utils.QueueMessage;
import com.eden.cron.service.ModelService;
import com.eden.cron.viewmodel.ModelVM;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Message consumer for model.
 */
@Component
@Log4j2
public class ModelConsumer {

    private ModelService modelService;

    /**
     * Process a message from kafka topic.
     *
     * @param message data to process
     */
    @KafkaListener(topics = "${cloudkarafka.topic}")
    public void processMessage(QueueMessage<ModelVM> message) {

        log.info("message received: {}", message);
        ModelVM processed = null;
        switch (message.getAction()) {
            case CREATE -> processed = modelService.createModel(message.getMessage());
            case UPDATE -> processed = modelService.updateModel(message.getMessage());
            case DELETE -> processed = modelService.deleteModel(message.getMessage().getId());
            default -> {
            }
        }
        log.info("processed {} model: {}", message.getAction(), processed);
    }

    /**
     * Setter.
     */
    @Autowired
    public void setModelService(ModelService modelService) {
        this.modelService = modelService;
    }
}
