package com.ibhuerli.URLShortener.services;

import com.ibhuerli.URLShortener.models.Statistic;
import com.ibhuerli.URLShortener.repositories.StatisticRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StatisticService {

    private final StatisticRepository statisticRepository;

    public StatisticService(StatisticRepository statisticRepository) {
        this.statisticRepository = statisticRepository;
    }

    public Optional<Statistic> getShortUrlStatistic(UUID id) {
        return this.statisticRepository.findById(id);
    }

    public List<Statistic> getAllShortUrlStatistics() {
        return this.statisticRepository.findAll();
    }

    public void addOrUpdateStatistic(UUID shortUrlId, long forwardDurationInMillis) {
        Optional<Statistic> result = this.statisticRepository.findById(shortUrlId);

        if (result.isEmpty()) {
            Statistic statistic = new Statistic();
            statistic.setShortUrlId(shortUrlId);
            statistic.incrementTotalNumberOfCalls();
            statistic.setAverageForwardDurationInMillis(forwardDurationInMillis);
            statistic.setTimeOfLastCall(LocalDateTime.now());

            this.statisticRepository.insert(statistic);
        } else {
            Statistic statistic = result.get();
            long beforeTotalCalls = statistic.getTotalNumberOfCalls();
            statistic.incrementTotalNumberOfCalls();
            long afterTotalCalls = statistic.getTotalNumberOfCalls();

            long newAverageTime = this.calculateNewAverage(beforeTotalCalls, afterTotalCalls, statistic.getAverageForwardDurationInMillis(), forwardDurationInMillis);

            statistic.setAverageForwardDurationInMillis(newAverageTime);
            statistic.setTimeOfLastCall(LocalDateTime.now());

            this.statisticRepository.save(statistic);
        }
    }

    public long calculateNewAverage(long beforeTotalCalls, long afterTotalCalls, long beforeAverageTime, long newForwardDurationInMillis){
        long time = beforeTotalCalls * beforeAverageTime;
        time += newForwardDurationInMillis;

        return (time / afterTotalCalls);
    }
}
