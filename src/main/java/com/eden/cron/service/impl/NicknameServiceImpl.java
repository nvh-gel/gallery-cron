package com.eden.cron.service.impl;

import com.eden.cron.mapper.NicknameMapper;
import com.eden.cron.model.Nickname;
import com.eden.cron.repository.NicknameRepository;
import com.eden.cron.service.NicknameService;
import com.eden.cron.viewmodel.NicknameVM;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NicknameServiceImpl implements NicknameService {

    private NicknameRepository nicknameRepository;

    private final NicknameMapper nicknameMapper = Mappers.getMapper(NicknameMapper.class);

    @Override
    public NicknameVM createNick(NicknameVM request) {

        Nickname nick = nicknameMapper.toModel(request);
        nick.setCreatedAt(LocalDateTime.now());
        nick.setUpdatedAt(LocalDateTime.now());

        Nickname created = nicknameRepository.save(nick);
        return nicknameMapper.toViewModel(created);
    }

    @Override
    public List<NicknameVM> findAllNicks() {

        List<Nickname> nicks = nicknameRepository.findAll();
        return nicknameMapper.toViewModel(nicks);
    }

    @Autowired
    public void setNicknameRepository(NicknameRepository nicknameRepository) {
        this.nicknameRepository = nicknameRepository;
    }
}
