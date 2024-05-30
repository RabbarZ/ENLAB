package com.ibhuerli.URLShortener.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Document("Statistic")
public class Statistic {
    @Id
    private UUID shortUrlId;

    private long totalNumberOfCalls;

    private long averageForwardDurationInMillis;

    private LocalDateTime timeOfLastCall;

    public Statistic(){
        this.totalNumberOfCalls = 0;
        this.averageForwardDurationInMillis = 0;
    }

    public UUID getShortUrlId() {
        return shortUrlId;
    }

    public void setShortUrlId(UUID shortUrlId) {
        this.shortUrlId = shortUrlId;
    }

    public long getTotalNumberOfCalls() {
        return totalNumberOfCalls;
    }

    public void setTotalNumberOfCalls(long totalNumberOfCalls) {
        this.totalNumberOfCalls = totalNumberOfCalls;
    }

    public void incrementTotalNumberOfCalls() {
        this.totalNumberOfCalls += 1;
    }

    public long getAverageForwardDurationInMillis() {
        return averageForwardDurationInMillis;
    }

    public void setAverageForwardDurationInMillis(long averageForwardDurationInMillis) {
        this.averageForwardDurationInMillis = averageForwardDurationInMillis;
    }

    public LocalDateTime getTimeOfLastCall() {
        return timeOfLastCall;
    }

    public void setTimeOfLastCall(LocalDateTime timeOfLastCall) {
        this.timeOfLastCall = timeOfLastCall;
    }
}
