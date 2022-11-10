package com.eden.cron.service;

import com.eden.common.service.BaseService;
import com.eden.cron.viewmodel.BatchPublisherVM;
import com.eden.cron.viewmodel.PublisherVM;

import java.util.List;

/***
 * Service for publisher handling
 */
public interface PublisherService extends BaseService<PublisherVM> {

    /**
     * Batch create publishers from list of names.
     *
     * @param request batch list of names
     * @return list of transaction uuids
     */
    List<String> batchCreate(BatchPublisherVM request);
}
