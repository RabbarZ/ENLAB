package com.ibhuerli.URLShortener.controllers;

import com.ibhuerli.URLShortener.services.ShortUrlService;
import com.ibhuerli.URLShortener.models.ShortUrl;
import com.ibhuerli.URLShortener.services.StatisticService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Optional;
import java.util.UUID;

@RestController
public class ShortenerController {
    private final ShortUrlService shortUrlService;
    private final StatisticService statisticService;

    public ShortenerController(ShortUrlService urlService, StatisticService statisticService) {
        this.shortUrlService = urlService;
        this.statisticService = statisticService;
    }

    @GetMapping("/a/{shortUrl}")
    public RedirectView redirectToUrl(@PathVariable String shortUrl) {
        long millisStart = System.currentTimeMillis();

        var result = this.shortUrlService.getShortUrl(shortUrl);

        // take time for statistics
        long millisEnd = System.currentTimeMillis();
        long duration = millisEnd - millisStart;

        if (result.isPresent()) {
            ShortUrl url = result.get();

            this.statisticService.addOrUpdateStatistic(url.getId(), duration);

            return new RedirectView(url.getUrl());
        } else {
            return null;
        }
    }

    @GetMapping("/shorturls")
    public String[] getShortUrls() {
        return this.shortUrlService.getShortUrls().stream().map(ShortUrl::getShortUrl).toArray(String[]::new);
    }

    @GetMapping("/shorturls/{id}")
    public String getShortUrl(@PathVariable UUID id) {
        Optional<ShortUrl> url = this.shortUrlService.getShortUrlById(id);
        if (url.isPresent()) {
            return url.get().getShortUrl();
        } else {
            return "short url not found";
        }
    }

    @PutMapping("/shorturls/{id}")
    public String updateOrUpdateShortUrl(@PathVariable UUID id, @RequestBody ShortUrl url) {
        this.shortUrlService.addOrUpdateShortUrl(url);
        return "updated short url";
    }

    @PutMapping("/shorturls")
    public String createOrUpdateShortUrl(@RequestBody ShortUrl url) {
        this.shortUrlService.addOrUpdateShortUrl(url);
        return "added short url";
    }

    @DeleteMapping("/shorturls/{id}")
    public String DeleteShortUrl(@PathVariable UUID id) {
        this.shortUrlService.deleteShortUrl(id);
        return "deleted short url";
    }
}
