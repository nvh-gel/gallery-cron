package com.eden.cron.service;

import com.eden.cron.viewmodel.ModelVM;

import java.util.List;

public interface ModelService {

    ModelVM createModel(ModelVM request);

    List<ModelVM> findAllModels();

    ModelVM findModelById(Long id);

    List<ModelVM> findModelByName(String name);
}
