package com.eden.cron.service;

import com.eden.cron.viewmodel.NicknameVM;

import java.util.List;

/**
 * Service for model nickname.
 */
public interface NicknameService {

    /**
     * Create a model nickname.
     *
     * @param request request data
     * @return created nickname
     */
    NicknameVM createNick(NicknameVM request);

    /**
     * Retrieve all model nicknames.
     *
     * @return list of nicknames
     */
    List<NicknameVM> findAllNicks();

    /**
     * Update a model nickname.
     *
     * @param request update request
     * @return updated nickname result
     */
    NicknameVM updateNick(NicknameVM request);

    /**
     * Mark a model nickname to deleted.
     *
     * @param id nickname Id to delete
     * @return deleted nickname
     */
    NicknameVM deleteNick(Long id);

    /**
     * Send a nick creation request to queue.
     *
     * @param request creation request data
     * @return transaction uuid
     */
    String createNickOnQueue(NicknameVM request);

    /**
     * Send a nick update request to queue.
     *
     * @param request update request
     * @return transaction uuid
     */
    String updateNickOnQueue(NicknameVM request);

    /**
     * Send a nick deletion request to queue.
     *
     * @param id nickname id to delete
     * @return transaction uuid
     */
    String deleteNickOnQueue(Long id);
}
