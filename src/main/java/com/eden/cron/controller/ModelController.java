package com.eden.cron.controller;

import com.eden.common.utils.ResponseModel;
import com.eden.cron.service.ModelService;
import com.eden.cron.viewmodel.ModelVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("model")
public class ModelController {

    private ModelService modelService;

    @PostMapping
    public ResponseEntity<ResponseModel> createModel(@RequestBody ModelVM request) {

        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(ResponseModel.created(modelService.createModel(request)));
    }

    @Autowired
    public void setModelService(ModelService modelService) {
        this.modelService = modelService;
    }
}
