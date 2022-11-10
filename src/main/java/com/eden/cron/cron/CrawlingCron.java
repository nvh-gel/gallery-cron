package com.eden.cron.cron;

import com.eden.cron.service.CrawlerService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Log4j2
public class CrawlingCron {

    private CrawlerService crawlerService;

    @Scheduled(cron = "0 * * * * *")
    public void crawlingJob() {
        log.info("this is a job run at {}", LocalDateTime.now());
        crawlerService.crawlForModel("https://mrcong.com");
    }

    /**
     * Setter.
     */
    @Autowired
    public void setCrawlerService(CrawlerService crawlerService) {
        this.crawlerService = crawlerService;
    }
}
