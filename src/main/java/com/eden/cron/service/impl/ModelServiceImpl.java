package com.eden.cron.service.impl;

import com.eden.cron.mapper.ModelMapper;
import com.eden.cron.model.Model;
import com.eden.cron.repository.ModelRepository;
import com.eden.cron.service.ModelService;
import com.eden.cron.viewmodel.ModelVM;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Implementation of model service.
 */
@Service
public class ModelServiceImpl implements ModelService {

    private ModelRepository modelRepository;

    private final ModelMapper modelMapper = Mappers.getMapper(ModelMapper.class);

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public ModelVM createModel(ModelVM request) {

        Model model = modelMapper.toModel(request);
        model.setCreatedAt(LocalDateTime.now());
        model.setUpdatedAt(LocalDateTime.now());
        Model created = modelRepository.save(model);
        return modelMapper.toViewModel(created);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ModelVM> findAllModels() {

        List<Model> models = modelRepository.findAll();
        return modelMapper.toViewModel(models);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ModelVM findModelById(Long id) {

        Model model = modelRepository.findById(id).orElse(null);
        return modelMapper.toViewModel(model);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ModelVM> findModelByName(String name) {

        List<Model> found = modelRepository.findAllByNameIgnoreCaseContaining(name);
        return modelMapper.toViewModel(found);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ModelVM> findModelName(String name) {

        List<Model> found = modelRepository.findAllByName(name);
        return modelMapper.toViewModel(found);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public ModelVM updateModel(ModelVM request) {

        Model model = modelRepository.findById(request.getId()).orElse(null);
        if (model == null) {
            return null;
        } else {
            modelMapper.mapUpdate(model, modelMapper.toModel(request));
            model.setUpdatedAt(LocalDateTime.now());
            modelRepository.save(model);
            return modelMapper.toViewModel(model);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public ModelVM deleteModel(Long id) {

        Model model = modelRepository.findById(id).orElse(null);
        if (model == null) {
            return null;
        } else {
            model.setDeleted(true);
            model.setUpdatedAt(LocalDateTime.now());
            modelRepository.save(model);
            return modelMapper.toViewModel(model);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public ModelVM removeModel(Long id) {

        Model model = modelRepository.findById(id).orElse(null);
        if (model == null) {
            return null;
        } else {
            modelRepository.delete(model);
            return modelMapper.toViewModel(model);
        }
    }

    @Autowired
    public void setModelRepository(ModelRepository modelRepository) {
        this.modelRepository = modelRepository;
    }
}
