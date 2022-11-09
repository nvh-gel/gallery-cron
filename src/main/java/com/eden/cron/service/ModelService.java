package com.eden.cron.service;

import com.eden.common.service.BaseService;
import com.eden.cron.viewmodel.ModelVM;

import java.util.List;

/**
 * Service for model.
 */
public interface ModelService extends BaseService<ModelVM> {

    /**
     * Find all models by name, native name or nickname.
     *
     * @param name name to find
     * @return list of found models
     */
    List<ModelVM> findModelByName(String name);
}
