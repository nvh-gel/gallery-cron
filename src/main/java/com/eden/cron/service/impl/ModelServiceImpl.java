package com.eden.cron.service.impl;

import com.eden.cron.repository.ModelRepository;
import com.eden.cron.model.Model;
import com.eden.cron.service.ModelService;
import com.eden.cron.viewmodel.ModelVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ModelServiceImpl implements ModelService {

    private ModelRepository modelRepository;

    @Override
    public ModelVM createModel(ModelVM request) {
        Model model = new Model();
        model.setName(request.getName());
        model.setNativeName(request.getNativeName());
        model.setCreatedAt(LocalDateTime.now());
        model.setUpdatedAt(LocalDateTime.now());

        Model created = modelRepository.save(model);

        ModelVM result = new ModelVM();
        result.setId(created.getId());
        result.setName(created.getName());
        result.setNativeName(created.getNativeName());
        result.setCreatedAt(created.getCreatedAt());
        result.setUpdatedAt(created.getUpdatedAt());
        return result;
    }

    @Autowired
    public void setModelRepository(ModelRepository modelRepository) {
        this.modelRepository = modelRepository;
    }
}
