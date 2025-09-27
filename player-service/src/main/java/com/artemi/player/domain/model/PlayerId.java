package com.artemi.player.domain.model;

public record PlayerId(String value) {
    public PlayerId {
        if (value == null || value.isBlank()) throw new IllegalArgumentException("playerId required");
    }

    @Override
    public String toString() {
        return value;
    }
}