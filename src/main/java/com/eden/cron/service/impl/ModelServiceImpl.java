package com.eden.cron.service.impl;

import com.eden.cron.mapper.ModelMapper;
import com.eden.cron.model.Model;
import com.eden.cron.repository.ModelRepository;
import com.eden.cron.service.ModelService;
import com.eden.cron.viewmodel.ModelVM;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ModelServiceImpl implements ModelService {

    private ModelRepository modelRepository;

    private final ModelMapper modelMapper = Mappers.getMapper(ModelMapper.class);

    @Override
    public ModelVM createModel(ModelVM request) {

        Model model = modelMapper.toModel(request);
        model.setCreatedAt(LocalDateTime.now());
        model.setUpdatedAt(LocalDateTime.now());
        Model created = modelRepository.save(model);
        return modelMapper.toViewModel(created);
    }

    @Override
    public List<ModelVM> findAllModels() {

        List<Model> models = modelRepository.findAll();
        return modelMapper.toViewModel(models);
    }


    @Autowired
    public void setModelRepository(ModelRepository modelRepository) {
        this.modelRepository = modelRepository;
    }
}
