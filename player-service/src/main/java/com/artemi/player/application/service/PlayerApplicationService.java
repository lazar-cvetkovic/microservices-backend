package com.artemi.player.application.service;

import com.artemi.player.domain.model.Player;
import com.artemi.player.domain.model.PlayerId;
import com.artemi.player.domain.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;

@RequiredArgsConstructor
public class PlayerApplicationService {
    private final PlayerRepository repository;
    private final ExperienceDomainService xpService;

    public Player upsertPlayer(String playerId, String playfabId, Integer power,
                               String username, String country, String city) {
        var id = new PlayerId(playerId);
        var existing = repository.findById(id);
        if (username != null && repository.findByUsernameIgnoreCase(username).isPresent()
                && existing.map(p -> !username.equalsIgnoreCase(p.getUsername())).orElse(true)) {
            throw new IllegalArgumentException("Username taken");
        }

        Player p = existing.orElseGet(() -> Player.builder()
                .id(id)
                .playfabPlayerId(playfabId)
                .power(power != null ? power : 0)
                .username(username)
                .country(country != null ? country : "RS")
                .city(city != null ? city : "Belgrade")
                .level(1)
                .experience(BigDecimal.ZERO)
                .dailyLoginStreak(0)
                .lastLogin(Instant.now())
                .dailyRewardClaimed(false)
                .petSlotsUnlocked(1)
                .build());

        p.setPlayfabPlayerId(playfabId);
        p.setUsernameCityCountry(username, city, country);
        return repository.save(p);
    }

    public ExperienceDomainService.LevelView addExperience(String playerId, long amount) {
        var p = repository.findById(new PlayerId(playerId)).orElseThrow();
        xpService.addExperience(p, amount);
        repository.save(p);
        return xpService.toView(p);
    }

    public ExperienceDomainService.LevelView getLevel(String playerId) {
        var p = repository.findById(new PlayerId(playerId)).orElseThrow();
        return xpService.toView(p);
    }

    public Player getWithPets(String playerId, List<String> pets) {
        return repository.findById(new PlayerId(playerId)).orElseThrow();
    }

    public void assignPetToSlot(String playerId, int slotIndex, String petInstanceId) {
        var p = repository.findById(new PlayerId(playerId)).orElseThrow();
        p.assignPetToSlot(slotIndex, petInstanceId);
        repository.save(p);
    }

    public void removePetFromSlot(String playerId, int slotIndex) {
        var p = repository.findById(new PlayerId(playerId)).orElseThrow();
        p.removePetFromSlot(slotIndex);
        repository.save(p);
    }

    public void unlockPetSkin(String playerId, int skinId, String petType) {
        var p = repository.findById(new PlayerId(playerId)).orElseThrow();
        p.unlockPetSkin(skinId, petType);
        repository.save(p);
    }

    public List<Player> leaderboardTop(int limit) {
        return repository.topByPower(limit);
    }

    public List<Player> leaderboardByFilter(String playerId, String field, int limit) {
        var self = repository.findById(new PlayerId(playerId)).orElseThrow();
        var value = switch (field.toLowerCase()) {
            case "country" -> self.getCountry();
            case "city" -> self.getCity();
            default -> throw new IllegalArgumentException("invalid filter");
        };
        var list = repository.topByPowerFiltered(field, value, limit);
        if (list.stream().noneMatch(p -> p.getId().equals(self.getId()))) list.add(self);
        return list;
    }
}
