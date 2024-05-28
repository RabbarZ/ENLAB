package com.ibhuerli.URLShortener.controllers;

import com.ibhuerli.URLShortener.services.ShortUrlService;
import com.ibhuerli.URLShortener.models.ShortUrl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.util.UUID;

@RestController
@RequestMapping("/shortener")
public class ShortenerController {

    private final ShortUrlService shortUrlService;

    public ShortenerController(ShortUrlService urlService) {
        this.shortUrlService = urlService;
    }

    @GetMapping("/shorturls")
    public String getShortUrls() throws MalformedURLException {
        ShortUrl url = new ShortUrl();
        url.setId(UUID.randomUUID());
        url.setUrl("youtube.com");
        url.setShortUrl("satan.com/asd");


        this.shortUrlService.addOrUpdateShortUrl(url);
        return "asd";
    }

    @GetMapping("/shorturls/{id}")
    public String getShortUrl(int id) {
        return "liefert eine einzelne ShortUrl";
    }

    @PutMapping("/shorturls/{id}")
    public ResponseEntity createOrUpdateShortUrl(int id) throws MalformedURLException {
        ShortUrl url = new ShortUrl();
        url.setId(UUID.randomUUID());
        url.setUrl("youtube.com");
        url.setShortUrl("satan.com/asd");


        this.shortUrlService.addOrUpdateShortUrl(url);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/shorturls/{id}")
    public String DeleteShortUrl(int id) {
        return "löscht eine ShortUrl";
    }
}
