package com.ibhuerli.URLShortener.controllers;

import com.ibhuerli.URLShortener.services.ShortUrlService;
import com.ibhuerli.URLShortener.models.ShortUrl;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.net.MalformedURLException;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/shortener")
public class ShortenerController {

    private final ShortUrlService shortUrlService;

    public ShortenerController(ShortUrlService urlService) {
        this.shortUrlService = urlService;
    }

    @GetMapping("/a/{shortUrl}")
    public RedirectView redirectToUrl(@PathVariable String shortUrl) throws MalformedURLException {
        var result = this.shortUrlService.getShortUrl(shortUrl);

        if (!result.isEmpty()) {
            return new RedirectView(result.get().getUrl());
        } else {
            return null;
        }
    }

    @GetMapping("/shorturls")
    public String[] getShortUrls() throws MalformedURLException {
        return this.shortUrlService.getShortUrls().stream().map(shortUrl -> {
            return shortUrl.getShortUrl();
        }).toArray(String[]::new);
    }

    @GetMapping("/shorturls/{id}")
    public String getShortUrl(UUID id) {
        System.out.println(id);
        Optional<ShortUrl> url = this.shortUrlService.getShortUrl(id);
        if (url.isEmpty()) {
            return "short url not found";
        } else {
            return url.get().getShortUrl();
        }
    }

    @PutMapping("/shorturls/{id}")
    public String updateOrUpdateShortUrl(@PathVariable UUID id, @RequestBody ShortUrl url) {
        this.shortUrlService.addOrUpdateShortUrl(url);
        return "updated short url";
    }

    @PutMapping("/shorturls")
    public String createOrUpdateShortUrl(@RequestBody ShortUrl url) {
        url.setId(UUID.randomUUID());
        this.shortUrlService.addOrUpdateShortUrl(url);
        return "added short url";
    }

    @DeleteMapping("/shorturls/{id}")
    public String DeleteShortUrl(@PathVariable UUID id) {
        this.shortUrlService.deleteShortUrl(id);
        return "deleted short url";
    }
}
