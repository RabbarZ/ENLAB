package com.ibhuerli.URLShortener.controllers;

import com.ibhuerli.URLShortener.models.Statistic;
import com.ibhuerli.URLShortener.services.StatisticService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class StatisticController {

    private final StatisticService statisticService;

    public StatisticController(StatisticService statisticService) {
        this.statisticService = statisticService;
    }

    @GetMapping("/statistics")
    public Statistic getStatistics() {
        List<Statistic> statistics = this.statisticService.getAllShortUrlStatistics();

        Statistic statistic = new Statistic();
        long totalCallsOverall = 0;
        long timeOverall = 0;
        LocalDateTime latestCall = LocalDateTime.MIN;
        UUID shortUrlIdOfLatestCall = null;

        for (Statistic item : statistics) {
            long totalCalls = item.getTotalNumberOfCalls();
            long averageTime = item.getAverageForwardDurationInMillis();

            totalCallsOverall += totalCalls;
            timeOverall += totalCalls * averageTime;

            // evaluate latest short Url call
            LocalDateTime timeOfLastCall = item.getTimeOfLastCall();
            if (timeOfLastCall.isAfter(latestCall)) {
                latestCall = timeOfLastCall;
                shortUrlIdOfLatestCall = item.getShortUrlId();
            }
        }

        statistic.setTotalNumberOfCalls(totalCallsOverall);
        statistic.setAverageForwardDurationInMillis(timeOverall / totalCallsOverall);
        statistic.setTimeOfLastCall(latestCall);
        statistic.setShortUrlId(shortUrlIdOfLatestCall);

        return statistic;
    }

    @GetMapping("/shorturls/{id}/statistics")
    public Statistic getShortUrlStatistics(@PathVariable UUID id) {
        Optional<Statistic> statistic = this.statisticService.getShortUrlStatistic(id);
        return statistic.orElse(null);
    }
}
