package com.ibhuerli.URLShortener.controllers;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShortenerController {

    @GetMapping("/shorturls")
    public String getShortUrls(){
        return "liefert Array mit ShortUrl’s";
    }

    @GetMapping("/shorturls/{id}")
    public String getShortUrl(int id){
        return "liefert eine einzelne ShortUrl";
    }

    @PutMapping("/shorturls/{id}")
    public String createOrUpdateShortUrl(int id){
        return "erzeugt oder aktualisiert die ShortUr";
    }

    @DeleteMapping("/shorturls/{id}")
    public String DeleteShortUrl(int id){
        return "löscht eine ShortUrl";
    }
}
