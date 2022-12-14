package com.eden.cron.cron;

import com.eden.cron.service.CrawlerService;
import com.eden.cron.utils.Constants;
import com.eden.cron.utils.ScheduleConstants;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Cron Scheduler for crawler
 */
@Component
@Log4j2
public class CrawlingCron {

    private CrawlerService crawlerService;

    /**
     * Schedule a running cron for crawling site.
     */
    @Scheduled(cron = ScheduleConstants.EVERY_MINUTE)
    public void crawlingJob() {

        crawlerService.crawlFullSite(Constants.SITE_MRCONG);
    }

    /**
     * Setter.
     */
    @Autowired
    public void setCrawlerService(CrawlerService crawlerService) {
        this.crawlerService = crawlerService;
    }
}
