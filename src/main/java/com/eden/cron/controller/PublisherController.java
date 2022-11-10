package com.eden.cron.controller;

import com.eden.common.utils.ResponseModel;
import com.eden.cron.service.PublisherService;
import com.eden.cron.viewmodel.BatchPublisherVM;
import com.eden.cron.viewmodel.PublisherVM;
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

@RestController
@RequestMapping("publisher")
public class PublisherController {

    private PublisherService publisherService;

    /**
     * Create a publisher.
     *
     * @param request creation request data
     * @return created transaction uuid
     */
    @PostMapping
    public ResponseEntity<ResponseModel> createPublisher(@RequestBody PublisherVM request) {

        return ResponseEntity.accepted()
                .body(ResponseModel.created(publisherService.createOnQueue(request)));
    }

    /**
     * Get all publishers.
     *
     * @return list of publishers
     */
    @GetMapping
    public ResponseEntity<ResponseModel> getAllPublisher() {

        return ResponseEntity.ok(ResponseModel.ok(publisherService.findAll()));
    }

    /**
     * Get a single publisher by id.
     *
     * @param id publisher id
     * @return found publisher
     */
    @GetMapping("/{id}")
    public ResponseEntity<ResponseModel> getPublisherById(@PathVariable Long id) {

        PublisherVM result = publisherService.findById(id);
        if (result == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ResponseModel.notFound());
        }
        return ResponseEntity.ok(ResponseModel.ok(result));
    }

    /**
     * Update a publisher.
     *
     * @param request update request data
     * @return transaction uuid
     */
    @PutMapping
    public ResponseEntity<ResponseModel> updatePublisher(@RequestBody PublisherVM request) {

        return ResponseEntity.accepted()
                .body(ResponseModel.updated(publisherService.updateOnQueue(request)));
    }

    /**
     * Delete a publisher.
     *
     * @param id publisher id to delete
     * @return transaction uuid
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseModel> deletePublisher(@PathVariable Long id) {

        return ResponseEntity.accepted()
                .body(ResponseModel.deleted(publisherService.deleteOnQueue(id)));
    }

    /**
     * Batch create from a list of names.
     *
     * @param request batch request data
     * @return list of transaction uuid
     */
    @PostMapping("/batch")
    public ResponseEntity<ResponseModel> batchCreatePublisher(@RequestBody BatchPublisherVM request) {

        return ResponseEntity.accepted()
                .body(ResponseModel.created(publisherService.batchCreate(request)));
    }

    /**
     * Setter.
     */
    @Autowired
    public void setPublisherService(PublisherService publisherService) {
        this.publisherService = publisherService;
    }
}
