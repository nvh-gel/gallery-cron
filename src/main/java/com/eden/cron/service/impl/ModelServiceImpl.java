package com.eden.cron.service.impl;

import com.eden.common.utils.Action;
import com.eden.cron.mapper.ModelMapper;
import com.eden.cron.model.Model;
import com.eden.cron.producer.ModelProducer;
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

    private ModelProducer modelProducer;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public ModelVM create(ModelVM request) {

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
    public List<ModelVM> findAll() {

        List<Model> models = modelRepository.findAll();
        return modelMapper.toViewModel(models);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ModelVM findById(Long id) {

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
    public ModelVM update(ModelVM request) {

        Model model = modelRepository.findById(request.getId()).orElse(null);
        if (model == null) {
            return null;
        }
        modelMapper.mapUpdate(model, modelMapper.toModel(request));
        model.setUpdatedAt(LocalDateTime.now());
        Model updated = modelRepository.save(model);
        return modelMapper.toViewModel(updated);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public ModelVM delete(Long id) {

        Model model = modelRepository.findById(id).orElse(null);
        if (model == null) {
            return null;
        }
        modelRepository.deleteById(id);
        model.setUpdatedAt(LocalDateTime.now());
        return modelMapper.toViewModel(model);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String createOnQueue(ModelVM request) {

        return this.modelProducer.sendProcessingMessageToQueue(Action.CREATE, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String updateOnQueue(ModelVM request) {

        return this.modelProducer.sendProcessingMessageToQueue(Action.UPDATE, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String deleteOnQueue(Long id) {

        ModelVM vm = new ModelVM();
        vm.setId(id);
        return this.modelProducer.sendProcessingMessageToQueue(Action.DELETE, vm);
    }

    @Autowired
    public void setModelRepository(ModelRepository modelRepository) {
        this.modelRepository = modelRepository;
    }

    @Autowired
    public void setModelProducer(ModelProducer modelProducer) {
        this.modelProducer = modelProducer;
    }
}
