package com.ibhuerli.URLShortener.repositories;

import com.ibhuerli.URLShortener.models.Statistic;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StatisticRepository extends MongoRepository<Statistic, UUID> {
}
