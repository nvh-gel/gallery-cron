package com.eden.cron.consumer;

import com.eden.common.utils.Action;
import com.eden.common.utils.QueueMessage;
import com.eden.cron.service.ModelService;
import com.eden.cron.service.NicknameService;
import com.eden.cron.viewmodel.ModelVM;
import com.eden.cron.viewmodel.NicknameVM;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.internal.Function;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Message consumer for model.
 */
@Component
@Log4j2
public class ModelConsumer {

    private ModelService modelService;

    private NicknameService nicknameService;

    private final Map<Action, Function<ModelVM, ModelVM>> modelActionMap = Map.of(
            Action.CREATE, x -> modelService.createModel(x),
            Action.UPDATE, x -> modelService.updateModel(x),
            Action.DELETE, x -> modelService.deleteModel(x.getId()));

    private final Map<Action, Function<NicknameVM, NicknameVM>> nickActionMap = Map.of(
            Action.CREATE, x -> nicknameService.createNick(x),
            Action.UPDATE, x -> nicknameService.updateNick(x),
            Action.DELETE, x -> nicknameService.deleteNick(x.getId()));

    /**
     * Process a message from kafka topic.
     *
     * @param message data to process
     */
    @KafkaListener(topics = "${cloudkarafka.topic.model}")
    public void processModelMessage(QueueMessage<ModelVM> message) {

        log.info("message received: {}", message);
        Function<ModelVM, ModelVM> function = modelActionMap.getOrDefault(message.getAction(), null);
        if (function != null) {
            ModelVM processed = function.apply(message.getMessage());
            log.info("processed {} model: {}", message.getAction(), processed);
        }
    }

    /**
     * Process a nickname message from kafka topic.
     *
     * @param message data to process
     */
    @KafkaListener(topics = "${cloudkarafka.topic.nick}")
    public void processNickMessage(QueueMessage<NicknameVM> message) {

        log.info("message received: {}", message);
        Function<NicknameVM, NicknameVM> function = nickActionMap.getOrDefault(message.getAction(), null);
        if (function != null) {
            NicknameVM processed = function.apply(message.getMessage());
            log.info("process {} nickname: {}", message.getAction(), processed);
        }
    }

    /**
     * Setter.
     */
    @Autowired
    public void setModelService(ModelService modelService) {
        this.modelService = modelService;
    }

    /**
     * Setter.
     */
    @Autowired
    public void setNicknameService(NicknameService nicknameService) {
        this.nicknameService = nicknameService;
    }
}
