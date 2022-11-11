package com.eden.cron.service;

/**
 * Business service for crawler.
 */
public interface CrawlerService {

    /**
     * Crawling site for model and album.
     *
     * @param url url to crawl
     */
    void crawlFullSite(String url);
}
