package com.eden.cron.consumer;

import com.eden.common.consumer.BaseConsumer;
import com.eden.common.utils.Action;
import com.eden.common.utils.QueueMessage;
import com.eden.cron.service.NicknameService;
import com.eden.cron.viewmodel.NicknameVM;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.function.UnaryOperator;

/**
 * Implementation of nickname consumer.
 */
@Component
@Log4j2
public class NicknameConsumer extends BaseConsumer<NicknameVM> {

    private final NicknameService nicknameService;

    /**
     * Constructor.
     *
     * @param nicknameService nickname service bean
     */
    public NicknameConsumer(NicknameService nicknameService) {

        this.nicknameService = nicknameService;

        this.actionMap.put(Action.CREATE, this.nicknameService::create);
        this.actionMap.put(Action.UPDATE, this.nicknameService::update);
        this.actionMap.put(Action.DELETE, x -> this.nicknameService.delete(x.getId()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @KafkaListener(topics = "${cloudkarafka.topic.nick}")
    public void processMessage(QueueMessage<NicknameVM> queueMessage) {

        log.info("message received: {}", queueMessage);
        UnaryOperator<NicknameVM> function = actionMap.getOrDefault(queueMessage.getAction(), null);
        if (function != null) {
            NicknameVM processed = function.apply(queueMessage.getMessage());
            log.info("process {} nickname: {}", queueMessage.getAction(), processed);
        }
    }
}
