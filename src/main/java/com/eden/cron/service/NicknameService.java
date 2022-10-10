package com.eden.cron.service;

import com.eden.cron.viewmodel.NicknameVM;

import java.util.List;

public interface NicknameService {

    NicknameVM createNick(NicknameVM request);

    List<NicknameVM> findAllNicks();
}
