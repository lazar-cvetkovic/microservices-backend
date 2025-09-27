package com.artemi.node.domain.model;

import lombok.*;
import java.time.Instant;

@Getter @Builder
public class Node {
    private final String nodeId;
    private String playerId;
    private double score;
    private String country;
    private String city;
    private Instant timeWhenUpdated;

    public void capture(String playerId, double score, String country, String city, Instant time) {
        this.playerId = playerId;
        this.score = score;
        this.country = country;
        this.city = city;
        this.timeWhenUpdated = time;
    }
}
