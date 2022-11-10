package com.eden.cron.service.impl;

import com.eden.common.utils.Action;
import com.eden.cron.model.Config;
import com.eden.cron.producer.AlbumProducer;
import com.eden.cron.producer.ModelProducer;
import com.eden.cron.repository.ConfigRepository;
import com.eden.cron.repository.PublisherRepository;
import com.eden.cron.service.CrawlerService;
import com.eden.cron.viewmodel.AlbumVM;
import com.eden.cron.viewmodel.ModelVM;
import lombok.extern.log4j.Log4j2;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Service
@Log4j2
public class CrawlerServiceImpl implements CrawlerService {

    private final ConfigRepository configRepository;

    private final AlbumProducer albumProducer;

    private final ModelProducer modelProducer;

    private final List<String> publishers;

    public CrawlerServiceImpl(ConfigRepository configRepository,
                              PublisherRepository publisherRepository,
                              AlbumProducer albumProducer,
                              ModelProducer modelProducer) {
        this.configRepository = configRepository;
        this.albumProducer = albumProducer;
        this.modelProducer = modelProducer;

        this.publishers = publisherRepository.findALlName();
    }

    @Override
    @Transactional(Transactional.TxType.REQUIRED)
    public void crawlForModel(String url) {

        try {
            Config pageConfig = configRepository.getById("PAGE");
            int page = pageConfig.getValue();
            if (page > 1200) {
                return;
            }

            String pageUrl = url + "/page/" + page;

            Document doc = Jsoup.connect(pageUrl).get();
            Elements articles = doc.select("article");
            articles.forEach(this::retrieveModel);
            this.updatePageConfig(page);
        } catch (IOException e) {
            log.error(e);
        }
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public void updatePageConfig(int page) {
        page++;
        Config pageConfig = new Config();
        pageConfig.setKey("PAGE");
        pageConfig.setValue(page);
        configRepository.save(pageConfig);
    }

    private void retrieveModel(Element element) {

        Element album = Objects.requireNonNull(element.selectFirst("h2")).selectFirst("a");
        assert album != null;
        String albumUrl = album.attr("href");
        String albumName = album.text();
        String albumThumbnail = Objects.requireNonNull(element.selectFirst("img")).attr("src");
        Elements tags = element.getElementsByAttributeValue("rel", "tag");
        StringBuilder albumTags = new StringBuilder();
        String modelName = null;
        String nativeName = null;
        String modelUrl = null;
        for (Element tag : tags) {
            String innerText = tag.text();
            if (isNativeName(innerText)) {
                nativeName = innerText;
                modelUrl = modelUrl == null ? tag.attr("href") : modelUrl;
                albumTags.append(" ").append(nativeName);
                continue;
            }
            if (isModelName(innerText)) {
                modelName = innerText;
                modelUrl = tag.attr("href");
                albumTags.append(" ").append(modelName);
            } else {
                albumTags.append(" ").append(innerText);
            }
        }

        AlbumVM albumVM = new AlbumVM(albumName, albumThumbnail, albumUrl, albumTags.toString().trim(),
                null, null);

        ModelVM modelVM = new ModelVM(modelName, nativeName, albumThumbnail, modelUrl,
                null, null);

        modelProducer.sendProcessingMessageToQueue(Action.CREATE, modelVM);
        albumProducer.sendProcessingMessageToQueue(Action.CREATE, albumVM);
    }

    private boolean isNativeName(String text) {
        return !text.toLowerCase().matches("[a-z ]+");
    }

    private boolean isPublisher(String text) {
        return publishers.contains(text);
    }

    private boolean isModelName(String text) {
        return !isPublisher(text);
    }
}
