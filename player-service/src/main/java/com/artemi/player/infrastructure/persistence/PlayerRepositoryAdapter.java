package com.artemi.player.infrastructure.persistence;

import com.artemi.player.domain.model.*;
import com.artemi.player.domain.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class PlayerRepositoryAdapter implements PlayerRepository {
    private final SpringDataPlayerJpaRepository jpa;

    @Override
    public Optional<Player> findById(PlayerId id) {
        return jpa.findById(id.value()).map(this::toDomain);
    }

    @Override
    public Optional<Player> findByUsernameIgnoreCase(String username) {
        return jpa.findByUsernameIgnoreCase(username).map(this::toDomain);
    }

    @Override
    public Player save(Player p) {
        var e = toEntity(p);
        var saved = jpa.save(e);
        return toDomain(saved);
    }

    @Override
    public List<Player> topByPower(int limit) {
        var list = jpa.findTop10ByOrderByPowerDesc();
        return list.stream().limit(limit).map(this::toDomain).toList();
    }

    @Override
    public List<Player> topByPowerFiltered(String field, String value, int limit) {
        List<JpaPlayerEntity> list = switch (field.toLowerCase()) {
            case "country" -> jpa.findTop10ByCountryOrderByPowerDesc(value);
            case "city" -> jpa.findTop10ByCityOrderByPowerDesc(value);
            default -> List.of();
        };
        return list.stream().limit(limit).map(this::toDomain).toList();
    }

    private Player toDomain(JpaPlayerEntity e) {
        var p = Player.builder()
                .id(new PlayerId(e.getPlayerId()))
                .playfabPlayerId(e.getPlayfabPlayerId())
                .power(e.getPower())
                .username(e.getUsername())
                .city(e.getCity())
                .country(e.getCountry())
                .level(e.getLevel())
                .experience(e.getExperience())
                .dailyLoginStreak(e.getDailyLoginStreak())
                .lastLogin(e.getLastLogin())
                .dailyRewardClaimed(e.isDailyRewardClaimed())
                .petSlotsUnlocked(e.getPetSlotsUnlocked())
                .build();

        e.getUnlocked().forEach(ux -> p.unlockPetSkin(ux.getSkinId(), ux.getPetType()));
        e.getSlots().forEach(s -> {
            if (s.getPetInstanceId() != null) p.assignPetToSlot(s.getSlotIndex(), s.getPetInstanceId());
        });
        return p;
    }

    private JpaPlayerEntity toEntity(Player p) {
        var e = new JpaPlayerEntity();
        e.setPlayerId(p.getId().value());
        e.setPlayfabPlayerId(p.getPlayfabPlayerId());
        e.setPower(p.getPower());
        e.setUsername(p.getUsername());
        e.setCity(p.getCity());
        e.setCountry(p.getCountry());
        e.setLevel(p.getLevel());
        e.setExperience(p.getExperience());
        e.setDailyLoginStreak(p.getDailyLoginStreak());
        e.setLastLogin(p.getLastLogin());
        e.setDailyRewardClaimed(p.isDailyRewardClaimed());
        e.setPetSlotsUnlocked(p.getPetSlotsUnlocked());

        e.setUnlocked(p.getUnlockedPetSkins().stream().map(s -> {
            var ux = new JpaUnlockedPetSkinEntity(e.getPlayerId(), s.getSkinId(), s.getPetType(), null);
            ux.setPlayer(e);
            return ux;
        }).collect(Collectors.toList()));

        e.setSlots(p.getAssignedPets().entrySet().stream().map(ent -> {
            var slot = new JpaAssignedPetSlotEntity(e.getPlayerId(), ent.getKey(), ent.getValue(), null);
            slot.setPlayer(e);
            return slot;
        }).collect(Collectors.toList()));

        return e;
    }
}
