package com.eden.cron.service;

import com.eden.cron.viewmodel.ModelVM;

import java.util.List;

/**
 * Service for model.
 */
public interface ModelService {

    /**
     * Create a model.
     *
     * @param request request data
     * @return created model
     */
    ModelVM createModel(ModelVM request);

    /**
     * Retrieve all models.
     *
     * @return list of models
     */
    List<ModelVM> findAllModels();

    /**
     * Find model by id.
     *
     * @param id model id to find
     * @return found model
     */
    ModelVM findModelById(Long id);

    /**
     * Find model by model name.
     *
     * @param name model name to find
     * @return found model
     */
    List<ModelVM> findModelByName(String name);

    /**
     * Find all models by name, native name or nickname.
     *
     * @param name name to find
     * @return list of found models
     */
    List<ModelVM> findModelName(String name);

    /**
     * Update a single model.
     *
     * @param request update request data
     * @return updated model
     */
    ModelVM updateModel(ModelVM request);

    /**
     * Soft delete a single model
     *
     * @param id model id to delete
     * @return deleted model
     */
    ModelVM deleteModel(Long id);

    /**
     * Delete a model from database
     *
     * @param id model id to delete
     * @return deleted model
     */
    ModelVM removeModel(Long id);
}
