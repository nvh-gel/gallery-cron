package com.eden.cron.service.impl;

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

import java.io.IOException;
import java.util.List;

@Service
@Log4j2
public class CrawlerServiceImpl implements CrawlerService {

    private final ConfigRepository configRepository;

    private final PublisherRepository publisherRepository;

    private final AlbumProducer albumProducer;

    private final ModelProducer modelProducer;

    private final List<String> publishers;

    public CrawlerServiceImpl(ConfigRepository configRepository,
                              PublisherRepository publisherRepository,
                              AlbumProducer albumProducer,
                              ModelProducer modelProducer) {
        this.configRepository = configRepository;
        this.publisherRepository = publisherRepository;
        this.albumProducer = albumProducer;
        this.modelProducer = modelProducer;

        this.publishers = this.publisherRepository.findALlName();
    }

    @Override
    public void crawlForModel(String url) {

        try {
            int page = configRepository.getById("PAGE").getPage();

            String pageUrl = url + "/page/" + page;

            Document doc = Jsoup.connect(pageUrl).get();
            Elements articles = doc.select("article");
            articles.forEach(this::retrieveModel);
        } catch (IOException e) {
            log.error(e);
        }
    }

    private void retrieveModel(Element element) {

        Element album = element.selectFirst("h2").selectFirst("a");
        String albumUrl = album.attr("href");
        String albumName = album.text();
        String albumThumbnail = element.selectFirst("img").attr("src");
        Elements tags = element.getElementsByAttributeValue("rel", "tag");
        StringBuilder albumTags = new StringBuilder();
        String modelName = "";
        String nativeName = "";
        String modelUrl = "";
        for (Element tag : tags) {
            String innerText = tag.text();
            if (isNativeName(innerText)) {
                nativeName = innerText;
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

        AlbumVM albumVM = new AlbumVM();
        albumVM.setName(albumName);
        albumVM.setUrl(albumUrl);
        albumVM.setThumbnail(albumThumbnail);
        albumVM.setTags(albumTags.toString());

        ModelVM modelVM = new ModelVM();
        modelVM.setName(modelName);
        modelVM.setThumbnail(albumThumbnail);
        modelVM.setNativeName(nativeName);
        modelVM.setUrl(modelUrl);

//        log.info("\n {}\n {}", modelVM, albumVM);
    }

    private boolean isNativeName(String text) {
        return !text.toLowerCase().matches("[a-z ]+");
    }

    private boolean isPublisher(String text) {
        return publishers.contains(text);
    }

    private boolean isModelName(String text) {
        return !isPublisher(text.toLowerCase());
    }
}
