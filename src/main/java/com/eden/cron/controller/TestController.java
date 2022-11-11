package com.eden.cron.controller;

import com.eden.cron.service.CrawlerService;
import com.eden.cron.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class TestController {

    private CrawlerService crawlerService;

    /**
     * Controller for test purpose.
     */
    @GetMapping
    public void test() {

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
