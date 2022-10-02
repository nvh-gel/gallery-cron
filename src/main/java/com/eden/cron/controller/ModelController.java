package com.eden.cron.controller;

import com.eden.common.utils.ResponseModel;
import com.eden.cron.viewmodel.ModelVM;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("model")
public class ModelController {

    @PostMapping
    public ResponseEntity<ResponseModel> createModel(@RequestBody ModelVM request) {

        return null;
    }
}
