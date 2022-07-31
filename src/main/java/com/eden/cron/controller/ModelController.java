package com.eden.cron.controller;

import com.eden.common.utils.ResponseModel;
import com.eden.cron.service.ModelService;
import com.eden.cron.viewmodel.ModelVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("model")
public class ModelController {

    private ModelService modelService;

    @PostMapping
    ResponseEntity<ResponseModel> createModel(@RequestBody ModelVM request) {

        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(ResponseModel.created(modelService.createModel(request)));
    }

    @GetMapping
    ResponseEntity<ResponseModel> getAllModels() {

        return ResponseEntity.ok(ResponseModel.ok(modelService.findAllModels()));
    }

    @GetMapping("/{id}")
    ResponseEntity<ResponseModel> getModelById(@PathVariable Long id) {

        return ResponseEntity.ok(ResponseModel.ok(modelService.findModelById(id)));
    }

    @GetMapping("/name")
    ResponseEntity<ResponseModel> getModelByName(@RequestParam("name") String name) {

        return ResponseEntity.ok(ResponseModel.ok(modelService.findModelByName(name)));
    }

    @Autowired
    public void setModelService(ModelService modelService) {
        this.modelService = modelService;
    }
}
