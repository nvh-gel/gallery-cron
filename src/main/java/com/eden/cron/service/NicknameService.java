package com.eden.cron.service;

import com.eden.cron.viewmodel.NicknameVM;

import java.util.List;

/**
 * Service for model nickname.
 */
public interface NicknameService {

    /**
     * Create a model nickname
     *
     * @param request request data
     * @return created nickname
     */
    NicknameVM createNick(NicknameVM request);

    /**
     * Retrieve all model nicknames
     *
     * @return list of nicknames
     */
    List<NicknameVM> findAllNicks();
}
