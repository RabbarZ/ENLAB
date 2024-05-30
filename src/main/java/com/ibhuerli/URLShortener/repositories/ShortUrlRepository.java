package com.ibhuerli.URLShortener.repositories;

import com.ibhuerli.URLShortener.models.ShortUrl;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ShortUrlRepository extends MongoRepository<ShortUrl, UUID> {
    Optional<ShortUrl> findByShortUrl(String shortUrl);
}
