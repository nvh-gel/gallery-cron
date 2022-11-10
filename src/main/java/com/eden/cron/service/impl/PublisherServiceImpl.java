package com.eden.cron.service.impl;

import com.eden.common.utils.Action;
import com.eden.cron.mapper.PublisherMapper;
import com.eden.cron.model.Publisher;
import com.eden.cron.producer.PublisherProducer;
import com.eden.cron.repository.PublisherRepository;
import com.eden.cron.service.PublisherService;
import com.eden.cron.viewmodel.BatchPublisherVM;
import com.eden.cron.viewmodel.PublisherVM;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Implementation of publisher service.
 */
@Service
public class PublisherServiceImpl implements PublisherService {

    private PublisherRepository publisherRepository;

    private final PublisherMapper publisherMapper = Mappers.getMapper(PublisherMapper.class);

    private PublisherProducer publisherProducer;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public PublisherVM create(PublisherVM request) {

        Publisher publisher = publisherMapper.toModel(request);
        publisher.setCreatedAt(LocalDateTime.now());
        publisher.setUpdatedAt(LocalDateTime.now());
        Publisher created = publisherRepository.save(publisher);
        return publisherMapper.toViewModel(created);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String createOnQueue(PublisherVM request) {

        return publisherProducer.sendProcessingMessageToQueue(Action.CREATE, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PublisherVM> findAll() {

        List<Publisher> result = publisherRepository.findAll();
        return publisherMapper.toViewModel(result);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PublisherVM findById(Long id) {

        Publisher result = publisherRepository.findById(id).orElse(null);
        return publisherMapper.toViewModel(result);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String updateOnQueue(PublisherVM request) {

        return publisherProducer.sendProcessingMessageToQueue(Action.UPDATE, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PublisherVM update(PublisherVM request) {

        Publisher exist = publisherRepository.findById(request.getId()).orElse(null);
        if (exist == null) {
            return null;
        }
        publisherMapper.mapUpdate(exist, publisherMapper.toModel(request));
        exist.setUpdatedAt(LocalDateTime.now());
        Publisher updated = publisherRepository.save(exist);
        return publisherMapper.toViewModel(updated);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String deleteOnQueue(Long id) {

        PublisherVM vm = new PublisherVM();
        vm.setId(id);
        return publisherProducer.sendProcessingMessageToQueue(Action.DELETE, vm);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PublisherVM delete(Long id) {

        Publisher exist = publisherRepository.findById(id).orElse(null);
        if (exist == null) {
            return null;
        }
        publisherRepository.deleteById(id);
        exist.setUpdatedAt(LocalDateTime.now());
        return publisherMapper.toViewModel(exist);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> batchCreate(BatchPublisherVM request) {

        return request.getNames().stream().map(x -> {
            PublisherVM vm = new PublisherVM();
            vm.setName(x);
            return publisherProducer.sendProcessingMessageToQueue(Action.CREATE, vm);
        }).toList();
    }

    /**
     * Setter.
     */
    @Autowired
    public void setPublisherRepository(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    /**
     * Setter.
     */
    @Autowired
    public void setPublisherProducer(PublisherProducer publisherProducer) {
        this.publisherProducer = publisherProducer;
    }

}
