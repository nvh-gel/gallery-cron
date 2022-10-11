package com.eden.cron.controller;

import com.eden.common.utils.ResponseModel;
import com.eden.cron.service.ModelService;
import com.eden.cron.service.NicknameService;
import com.eden.cron.viewmodel.ModelVM;
import com.eden.cron.viewmodel.NicknameVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("model")
public class ModelController {

    private ModelService modelService;

    private NicknameService nickNameService;

    @PostMapping
    public ResponseEntity<ResponseModel> createModel(@RequestBody ModelVM request) {

        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(ResponseModel.created(modelService.createModel(request)));
    }

    @GetMapping
    public ResponseEntity<ResponseModel> getAllModels() {

        return ResponseEntity.ok(ResponseModel.ok(modelService.findAllModels()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseModel> getModel(@PathVariable Long id) {

        ModelVM found = modelService.findModelById(id);
        if (found != null) {
            return ResponseEntity.ok(ResponseModel.ok(found));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseModel.notFound());
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<ResponseModel> getModel(@PathVariable String name) {

        return ResponseEntity.ok(ResponseModel.ok(modelService.findModelByName(name)));
    }

    @GetMapping("/all/{name}")
    public ResponseEntity<ResponseModel> findModelByName(@PathVariable String name) {

        return ResponseEntity.ok(ResponseModel.ok(modelService.findModelName(name)));
    }

    @PutMapping
    public ResponseEntity<ResponseModel> updateModel(@RequestBody ModelVM request) {

        ModelVM result = modelService.updateModel(request);
        if (result == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ResponseModel.notFound());
        }
        return ResponseEntity.accepted().body(ResponseModel.updated(result));
    }

    @PostMapping("/nick")
    public ResponseEntity<ResponseModel> createNick(@RequestBody NicknameVM request) {

        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(ResponseModel.created(nickNameService.createNick(request)));
    }

    @GetMapping("/nick")
    public ResponseEntity<ResponseModel> getAllNicks() {

        return ResponseEntity.ok(ResponseModel.ok(nickNameService.findAllNicks()));
    }

    @Autowired
    public void setModelService(ModelService modelService) {
        this.modelService = modelService;
    }

    @Autowired
    public void setNickNameService(NicknameService nickNameService) {
        this.nickNameService = nickNameService;
    }
}
