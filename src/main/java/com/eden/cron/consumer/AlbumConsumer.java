package com.eden.cron.consumer;

import com.eden.common.consumer.BaseConsumer;
import com.eden.common.utils.Action;
import com.eden.common.utils.QueueMessage;
import com.eden.cron.service.AlbumService;
import com.eden.cron.utils.Constants;
import com.eden.cron.viewmodel.AlbumVM;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.function.UnaryOperator;

/**
 * Kafka message consumer for album.
 */
@Component
@Log4j2
public class AlbumConsumer extends BaseConsumer<AlbumVM> {

    private final AlbumService albumService;

    /**
     * Constructor.
     */
    public AlbumConsumer(AlbumService albumService) {

        this.albumService = albumService;

        this.actionMap.put(Action.CREATE, this.albumService::create);
        this.actionMap.put(Action.UPDATE, this.albumService::update);
        this.actionMap.put(Action.DELETE, x -> this.albumService.delete(x.getId()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void processMessage(QueueMessage<AlbumVM> queueMessage) {

        log.info(Constants.RECEIVED_MESSAGE, queueMessage);
        UnaryOperator<AlbumVM> function = actionMap.getOrDefault(queueMessage.getAction(), null);
        if (function != null) {
            AlbumVM processed = function.apply(queueMessage.getMessage());
            log.info(Constants.PROCESSED_MESSAGE, queueMessage.getAction(), processed);
        }
    }
}
