package com.eden.cron.service.impl;

import com.eden.cron.service.CrawlerService;
import lombok.extern.log4j.Log4j2;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Log4j2
public class CrawlerServiceImpl implements CrawlerService {

    private static final int MAX_PAGE = 2;

    /**
     * {@inheritDoc}
     */
    @Override
    public void crawlSite(String url) {

        try {
            int page = 1;
            while (page < MAX_PAGE) {
                String pageUrl = url + "/page/" + page;
                Document doc = Jsoup.connect(pageUrl).get();
                Elements articles = doc.select("article");
                articles.forEach(this::parseArticle);
                page++;
            }
        } catch (IOException e) {
            log.error(e);
        }
    }

    private void parseArticle(Element element) {
        log.info(element);
    }
}
