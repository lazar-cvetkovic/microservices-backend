package com.artemi.player.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;

@Entity
@Table(name = "player")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JpaPlayerEntity {
    @Id
    @Column(name = "player_id")
    private String playerId;
    private String playfabPlayerId;
    private int power;
    @Column(unique = true)
    private String username;
    private String city;
    private String country;
    private int level;
    private BigDecimal experience;
    private int dailyLoginStreak;
    private Instant lastLogin;
    private boolean dailyRewardClaimed;
    private int petSlotsUnlocked;

    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<JpaUnlockedPetSkinEntity> unlocked = new ArrayList<>();

    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<JpaAssignedPetSlotEntity> slots = new ArrayList<>();
}
