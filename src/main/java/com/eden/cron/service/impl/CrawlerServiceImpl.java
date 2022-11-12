package com.eden.cron.service.impl;

import com.eden.common.utils.Action;
import com.eden.cron.model.Config;
import com.eden.cron.producer.AlbumProducer;
import com.eden.cron.producer.ModelProducer;
import com.eden.cron.repository.ConfigRepository;
import com.eden.cron.repository.PublisherRepository;
import com.eden.cron.service.CrawlerService;
import com.eden.cron.utils.Constants;
import com.eden.cron.viewmodel.AlbumVM;
import com.eden.cron.viewmodel.ModelVM;
import lombok.extern.log4j.Log4j2;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Implementation for crawler service.
 */
@Service
@Log4j2
public class CrawlerServiceImpl implements CrawlerService {

    private final ConfigRepository configRepository;

    private final AlbumProducer albumProducer;

    private final ModelProducer modelProducer;

    private final List<String> publishers;

    /**
     * Constructor.
     */
    public CrawlerServiceImpl(ConfigRepository configRepository,
                              PublisherRepository publisherRepository,
                              AlbumProducer albumProducer,
                              ModelProducer modelProducer) {

        this.configRepository = configRepository;
        this.albumProducer = albumProducer;
        this.modelProducer = modelProducer;

        this.publishers = publisherRepository.findALlName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void crawlFullSite(String url) {

        boolean isOn = configRepository.getById(Constants.CRAWL_FULL_CONFIG).getValue() == 1;
        if (!isOn) {
            return;
        }
        try {
            Config pageConfig = configRepository.getById(Constants.PAGE_CONFIG);
            Config maxPageConfig = configRepository.getById(Constants.MAX_PAGE_CONFIG);
            int page = pageConfig.getValue();
            int maxPage = maxPageConfig.getValue();
            if (page > maxPage) {
                return;
            }

            String pageUrl = url + "/page/" + page;

            Document doc = Jsoup.connect(pageUrl).get();
            Elements articles = doc.select("article");
            articles.forEach(this::parseArticle);
            this.updatePageConfig(page);
        } catch (IOException e) {
            log.error(e);
        }
    }

    /**
     * Page increment and save config.
     *
     * @param page page to save
     */
    @Transactional
    public void updatePageConfig(int page) {

        page++;
        Config pageConfig = new Config();
        pageConfig.setKey(Constants.PAGE_CONFIG);
        pageConfig.setValue(page);
        configRepository.save(pageConfig);
    }

    /**
     * Parse an article and send data to queue.
     *
     * @param article article element to parse
     */
    private void parseArticle(Element article) {

        try {
            ModelVM modelVM = parseModel(article);
            AlbumVM albumVM = parseAlbum(article);

            modelProducer.sendProcessingMessageToQueue(Action.CREATE, modelVM);
            albumProducer.sendProcessingMessageToQueue(Action.CREATE, albumVM);
        } catch (NullPointerException e) {
            log.error("null pointer when processing article {}", article.selectFirst("a"));
            log.error(e);
        }
    }

    /**
     * Parse article element to model data.
     *
     * @param article article html to parse
     * @return model data
     */
    private ModelVM parseModel(Element article) {

        String modelName = null;
        String nativeName = null;
        String modelUrl = null;
        String modelThumbnail = Objects.requireNonNull(article.selectFirst("img")).attr("src");

        Elements tags = article.getElementsByAttributeValue("rel", "tag");
        for (Element tag : tags) {
            String innerText = tag.text();
            if (!isPublisher(innerText)) {
                if (isNativeName(innerText)) {
                    nativeName = innerText;
                    modelUrl = modelUrl == null ? tag.attr("href") : modelUrl;
                    continue;
                }
                modelName = innerText;
                modelUrl = tag.attr("href");
            }
        }

        return new ModelVM(modelName, nativeName, modelThumbnail, modelUrl, false,
                null, null);
    }

    /**
     * Parse an article element to album.
     *
     * @param article article html to parse
     * @return album vm
     */
    private AlbumVM parseAlbum(Element article) {

        Element album = Objects.requireNonNull(article.selectFirst("h2")).selectFirst("a");
        if (album == null) {
            return null;
        }

        String albumUrl = album.attr("href");
        String albumName = album.text();
        String albumThumbnail = Objects.requireNonNull(article.selectFirst("img")).attr("src");
        String albumTags = article.getElementsByAttributeValue("rel", "tag").stream()
                .map(Element::text)
                .collect(Collectors.joining(" "));

        return new AlbumVM(albumName, albumThumbnail, albumUrl, albumTags, false,
                null, null);
    }

    /**
     * Check if string contains non-latin characters.
     *
     * @param text string to check.
     * @return false if only latin characters
     */
    private boolean isNativeName(String text) {
        return !text.matches("^[\\p{Print}\\p{IsLatin}]*$");
    }

    /**
     * Check if string is within publisher names.
     *
     * @param text string to check
     * @return true if it is publisher name
     */
    private boolean isPublisher(String text) {
        return publishers.contains(text);
    }
}
