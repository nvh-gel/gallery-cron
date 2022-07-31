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

    private ModelRepository repository;

    private final ModelMapper mapper = Mappers.getMapper(ModelMapper.class);

    @Override
    public List<ModelVM> findAllModels() {
        List<Model> models = repository.findAll();
        return mapper.toViewModel(models);
    }

    @Override
    public ModelVM findModelById(Long id) {
        return null;
    }

    @Override
    public List<ModelVM> findModelByName(String name) {
        return null;
    }

    @Override
    public ModelVM createModel(ModelVM request) {
        Model model = mapper.toModel(request);
        model.setCreatedAt(LocalDateTime.now());
        model.setUpdatedAt(LocalDateTime.now());
        Model created = repository.save(model);
        return mapper.toViewModel(created);
    }

    @Autowired
    public void setRepository(ModelRepository repository) {
        this.repository = repository;
    }
}
