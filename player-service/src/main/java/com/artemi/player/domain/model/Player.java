package com.artemi.player.domain.model;

import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;

@Getter
public class Player {
    private final PlayerId id;
    private String playfabPlayerId;
    private int power;
    private String username;
    private String city;
    private String country;
    private int level;
    private BigDecimal experience;
    private int dailyLoginStreak;
    private Instant lastLogin;
    private boolean dailyRewardClaimed;
    private int petSlotsUnlocked;

    private final List<UnlockedPetSkin> unlockedPetSkins = new ArrayList<>();
    private final Map<Integer, String> assignedPets = new HashMap<>();

    @Builder
    private Player(PlayerId id, String playfabPlayerId, int power, String username, String city, String country,
                   int level, BigDecimal experience, int dailyLoginStreak, Instant lastLogin,
                   boolean dailyRewardClaimed, int petSlotsUnlocked) {
        this.id = id;
        this.playfabPlayerId = playfabPlayerId;
        this.power = power;
        this.username = username;
        this.city = city;
        this.country = country;
        this.level = level;
        this.experience = experience != null ? experience : BigDecimal.ZERO;
        this.dailyLoginStreak = dailyLoginStreak;
        this.lastLogin = lastLogin != null ? lastLogin : Instant.now();
        this.dailyRewardClaimed = dailyRewardClaimed;
        this.petSlotsUnlocked = Math.max(1, petSlotsUnlocked);
    }

    public void assignPetToSlot(int slotIndex, String petInstanceId) {
        if (slotIndex < 0 || slotIndex >= petSlotsUnlocked) throw new IllegalArgumentException("invalid slot");
        if (petInstanceId == null || petInstanceId.isBlank())
            throw new IllegalArgumentException("petInstanceId required");
        if (assignedPets.containsValue(petInstanceId)) throw new IllegalStateException("pet already assigned");
        assignedPets.put(slotIndex, petInstanceId);
    }

    public void removePetFromSlot(int slotIndex) {
        if (slotIndex < 0 || slotIndex >= petSlotsUnlocked) throw new IllegalArgumentException("invalid slot");
        assignedPets.remove(slotIndex);
    }

    public void unlockPetSkin(int skinId, String petType) {
        boolean exists = unlockedPetSkins.stream()
                .anyMatch(s -> s.getSkinId() == skinId && s.getPetType().equals(petType));
        if (!exists) unlockedPetSkins.add(new UnlockedPetSkin(skinId, petType));
    }

    public void setUsernameCityCountry(String username, String city, String country) {
        this.username = username;
        this.city = city;
        this.country = country;
    }

    public void setPlayfabPlayerId(String playfabPlayerId) {
        this.playfabPlayerId = playfabPlayerId;
    }
}