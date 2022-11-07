package com.eden.cron.service.impl;

import com.eden.common.utils.Action;
import com.eden.common.utils.QueueMessage;
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
import java.util.UUID;

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

    /**
     * {@inheritDoc}
     */
    @Override
    public String createModelOnQueue(ModelVM request) {

        return sendProcessingModelToQueue(Action.CREATE, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String updateModelOnQueue(ModelVM request) {

        return sendProcessingModelToQueue(Action.UPDATE, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String deleteModelOnQueue(Long id) {

        ModelVM vm = new ModelVM();
        vm.setId(id);
        return sendProcessingModelToQueue(Action.DELETE, vm);
    }

    /**
     * Craft a queue message and send to kafka topic.
     *
     * @param action  processing action {CREATE, UPDATE, DELETE}
     * @param request processing data
     * @return transaction uuid
     */
    private String sendProcessingModelToQueue(Action action, ModelVM request) {

        UUID uuid = UUID.randomUUID();
        QueueMessage<ModelVM> message = new QueueMessage<>(action, uuid, request);
        modelProducer.send(message);
        return uuid.toString();
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
