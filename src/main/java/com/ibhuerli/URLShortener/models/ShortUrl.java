package com.ibhuerli.URLShortener.models;

import java.net.URL;
import java.util.UUID;

public class ShortUrl {
    private UUID id;

    private String shortUrl;

    private URL Url;


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public URL getUrl() {
        return Url;
    }

    public void setUrl(URL url) {
        Url = url;
    }
}
