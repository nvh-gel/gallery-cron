package com.eden.cron.controller;

import com.eden.cron.service.CrawlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class TestController {

    private CrawlerService crawlerService;

    @GetMapping
    public ResponseEntity<String> testCrawling() {

        crawlerService.crawlForModel("https://mrcong.com");
        return ResponseEntity.ok("success");
    }

    /**
     * Setter.
     */
    @Autowired
    public void setCrawlerService(CrawlerService crawlerService) {
        this.crawlerService = crawlerService;
    }
}
