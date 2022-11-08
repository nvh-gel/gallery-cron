package com.eden.cron.service.impl;

import com.eden.common.utils.Action;
import com.eden.common.utils.QueueMessage;
import com.eden.cron.mapper.NicknameMapper;
import com.eden.cron.model.Nickname;
import com.eden.cron.producer.ModelProducer;
import com.eden.cron.repository.NicknameRepository;
import com.eden.cron.service.NicknameService;
import com.eden.cron.viewmodel.NicknameVM;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Implementation of nickname service.
 */
@Service
public class NicknameServiceImpl implements NicknameService {

    private NicknameRepository nicknameRepository;

    private final NicknameMapper nicknameMapper = Mappers.getMapper(NicknameMapper.class);

    private ModelProducer modelProducer;

    /**
     * {@inheritDoc}
     */
    @Override
    public NicknameVM createNick(NicknameVM request) {

        Nickname nick = nicknameMapper.toModel(request);
        nick.setCreatedAt(LocalDateTime.now());
        nick.setUpdatedAt(LocalDateTime.now());

        Nickname created = nicknameRepository.save(nick);
        return nicknameMapper.toViewModel(created);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<NicknameVM> findAllNicks() {

        List<Nickname> nicks = nicknameRepository.findAll();
        return nicknameMapper.toViewModel(nicks);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NicknameVM updateNick(NicknameVM request) {

        Nickname nick = nicknameRepository.findById(request.getId()).orElse(null);
        if (nick == null) {
            return null;
        }
        Nickname toUpdate = nicknameMapper.toModel(request);
        nicknameMapper.mapUpdate(nick, toUpdate);
        nick.setUpdatedAt(LocalDateTime.now());
        Nickname updated = nicknameRepository.save(nick);
        return nicknameMapper.toViewModel(updated);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NicknameVM deleteNick(Long id) {

        Nickname nick = nicknameRepository.findById(id).orElse(null);
        if (nick == null) {
            return null;
        }
        nick.setDeleted(true);
        nick.setUpdatedAt(LocalDateTime.now());
        Nickname deleted = nicknameRepository.save(nick);
        return nicknameMapper.toViewModel(deleted);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String createNickOnQueue(NicknameVM request) {

        return sendNickMessageToQueue(Action.CREATE, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String updateNickOnQueue(NicknameVM request) {

        return sendNickMessageToQueue(Action.UPDATE, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String deleteNickOnQueue(Long id) {

        NicknameVM requestData = new NicknameVM();
        requestData.setId(id);
        return sendNickMessageToQueue(Action.DELETE, requestData);
    }

    /**
     * Send a nick processing message to queue.
     *
     * @param action  {CREATE, UPDATE, DELETE}
     * @param request request data
     * @return transaction uuid
     */
    private String sendNickMessageToQueue(Action action, NicknameVM request) {

        UUID uuid = UUID.randomUUID();
        QueueMessage<NicknameVM> message = new QueueMessage<>(action, uuid, request);
        modelProducer.sendNick(message);
        return uuid.toString();
    }

    /**
     * Setter.
     */
    @Autowired
    public void setNicknameRepository(NicknameRepository nicknameRepository) {
        this.nicknameRepository = nicknameRepository;
    }

    /**
     * Setter.
     */
    @Autowired
    public void setModelProducer(ModelProducer modelProducer) {
        this.modelProducer = modelProducer;
    }
}
