package com.eden.cron.service.impl;

import com.eden.common.utils.Action;
import com.eden.cron.mapper.NicknameMapper;
import com.eden.cron.model.Nickname;
import com.eden.cron.producer.NicknameProducer;
import com.eden.cron.repository.NicknameRepository;
import com.eden.cron.service.NicknameService;
import com.eden.cron.viewmodel.NicknameVM;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Implementation of nickname service.
 */
@Service
public class NicknameServiceImpl implements NicknameService {

    private NicknameRepository nicknameRepository;

    private final NicknameMapper nicknameMapper = Mappers.getMapper(NicknameMapper.class);

    private NicknameProducer nicknameProducer;

    /**
     * {@inheritDoc}
     */
    @Override
    public NicknameVM create(NicknameVM request) {

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
    public List<NicknameVM> findAll() {

        List<Nickname> nicks = nicknameRepository.findAll();
        return nicknameMapper.toViewModel(nicks);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NicknameVM findById(Long id) {

        Nickname nick = nicknameRepository.findById(id).orElse(null);
        return nicknameMapper.toViewModel(nick);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NicknameVM update(NicknameVM request) {

        Nickname nick = nicknameRepository.findById(request.getId()).orElse(null);
        if (nick == null) {
            return null;
        }
        nicknameMapper.mapUpdate(nick, nicknameMapper.toModel(request));
        nick.setUpdatedAt(LocalDateTime.now());
        Nickname updated = nicknameRepository.save(nick);
        return nicknameMapper.toViewModel(updated);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NicknameVM delete(Long id) {

        Nickname nick = nicknameRepository.findById(id).orElse(null);
        if (nick == null) {
            return null;
        }
        nicknameRepository.deleteById(id);
        nick.setUpdatedAt(LocalDateTime.now());
        return nicknameMapper.toViewModel(nick);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String createOnQueue(NicknameVM request) {

        return nicknameProducer.sendProcessingMessageToQueue(Action.CREATE, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String updateOnQueue(NicknameVM request) {

        return nicknameProducer.sendProcessingMessageToQueue(Action.UPDATE, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String deleteOnQueue(Long id) {

        NicknameVM requestData = new NicknameVM();
        requestData.setId(id);
        return nicknameProducer.sendProcessingMessageToQueue(Action.DELETE, requestData);
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
    public void setNicknameProducer(NicknameProducer nicknameProducer) {
        this.nicknameProducer = nicknameProducer;
    }
}
