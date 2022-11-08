package com.eden.cron.controller;

import com.eden.common.utils.ResponseModel;
import com.eden.cron.service.ModelService;
import com.eden.cron.viewmodel.ModelVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
 * Rest controller to handle models.
 */
@RestController
@RequestMapping("model")
public class ModelController {

    private ModelService modelService;

    /**
     * Create model from request.
     *
     * @param request request data
     * @return created model
     */
    @PostMapping
    public ResponseEntity<ResponseModel> createModel(@RequestBody ModelVM request) {

        return ResponseEntity.accepted()
                .body(ResponseModel.created(modelService.createOnQueue(request)));
    }

    /**
     * Find all models.
     *
     * @return list of models
     */
    @GetMapping
    public ResponseEntity<ResponseModel> getAllModels() {

        return ResponseEntity.ok(ResponseModel.ok(modelService.findAll()));
    }

    /**
     * Find a single model by id.
     *
     * @param id id to find
     * @return found model
     */
    @GetMapping("/{id}")
    public ResponseEntity<ResponseModel> getModel(@PathVariable Long id) {

        ModelVM found = modelService.findById(id);
        if (found != null) {
            return ResponseEntity.ok(ResponseModel.ok(found));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseModel.notFound());
    }

    /**
     * Find models by name.
     *
     * @param name name to find
     * @return found models
     */
    @GetMapping("/name/{name}")
    public ResponseEntity<ResponseModel> getModel(@PathVariable String name) {

        return ResponseEntity.ok(ResponseModel.ok(modelService.findModelByName(name)));
    }

    /**
     * Find models by name, nickname, native name.
     *
     * @param name name to find
     * @return found models
     */
    @GetMapping("/all/{name}")
    public ResponseEntity<ResponseModel> findModelByName(@PathVariable String name) {

        return ResponseEntity.ok(ResponseModel.ok(modelService.findModelName(name)));
    }

    /**
     * Update a model.
     *
     * @param request update request data
     * @return updated model
     */
    @PutMapping
    public ResponseEntity<ResponseModel> updateModel(@RequestBody ModelVM request) {

        return ResponseEntity.accepted()
                .body(ResponseModel.updated(modelService.createOnQueue(request)));
    }

    /**
     * Soft delete a model.
     *
     * @param id model id to delete
     * @return deleted model
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseModel> deleteModel(@PathVariable Long id) {

        return ResponseEntity.accepted()
                .body(ResponseModel.deleted(modelService.deleteOnQueue(id)));
    }

    /**
     * Setter.
     */
    @Autowired
    public void setModelService(ModelService modelService) {
        this.modelService = modelService;
    }
}
