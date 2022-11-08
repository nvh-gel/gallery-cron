package com.eden.cron.controller;

import com.eden.common.utils.ResponseModel;
import com.eden.cron.service.NicknameService;
import com.eden.cron.viewmodel.NicknameVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest controller to handle model nickname.
 */
@RestController
@RequestMapping("nick")
public class NicknameController {

    private NicknameService nickNameService;

    /**
     * Create a nickname for model.
     *
     * @param request nick request data
     * @return created nickname
     */
    @PostMapping
    public ResponseEntity<ResponseModel> createNick(@RequestBody NicknameVM request) {

        return ResponseEntity.accepted()
                .body(ResponseModel.created(nickNameService.createOnQueue(request)));
    }

    /**
     * Get all models nickname.
     *
     * @return list of nicknames
     */
    @GetMapping
    public ResponseEntity<ResponseModel> getAllNicks() {

        return ResponseEntity.ok(ResponseModel.ok(nickNameService.findAll()));
    }

    /**
     * Update a nickname.
     *
     * @param request nick update data
     * @return update result
     */
    @PutMapping
    public ResponseEntity<ResponseModel> updateNick(@RequestBody NicknameVM request) {

        return ResponseEntity.accepted()
                .body(ResponseModel.updated(nickNameService.updateOnQueue(request)));
    }

    /***
     * Delete a nickname.
     *
     * @param id nickname id to delete
     * @return deleted result
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseModel> deleteNick(@PathVariable Long id) {

        return ResponseEntity.accepted()
                .body(ResponseModel.deleted(nickNameService.deleteOnQueue(id)));
    }

    /**
     * Setter.
     */
    @Autowired
    public void setNickNameService(NicknameService nickNameService) {
        this.nickNameService = nickNameService;
    }
}
