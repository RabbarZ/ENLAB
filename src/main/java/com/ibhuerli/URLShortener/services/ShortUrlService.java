package com.ibhuerli.URLShortener.services;

import com.ibhuerli.URLShortener.models.ShortUrl;
import com.ibhuerli.URLShortener.repositories.ShortUrlRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ShortUrlService {

    private final ShortUrlRepository shortUrlRepository;

    public ShortUrlService(ShortUrlRepository shortUrlRepository) {
        this.shortUrlRepository = shortUrlRepository;
    }

    public void addOrUpdateShortUrl(ShortUrl shortUrl) {
        Optional<ShortUrl> result = this.shortUrlRepository.findById(shortUrl.getId());

        if(result.isEmpty()){
            this.shortUrlRepository.insert(shortUrl);
        } else{
            ShortUrl url = result.get();
            url.setUrl(shortUrl.getUrl());
            url.setShortUrl(shortUrl.getShortUrl());
            this.shortUrlRepository.save(url);
        }
    }
}
