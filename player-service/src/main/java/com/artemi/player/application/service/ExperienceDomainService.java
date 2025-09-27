package com.artemi.player.application.service;

import com.artemi.player.domain.model.Player;

import java.math.BigDecimal;

public class ExperienceDomainService {
    private static final BigDecimal BASE_XP = new BigDecimal("329.0");
    private static final BigDecimal INCREMENT = new BigDecimal("1.0674");

    public void addExperience(Player player, long amount) {
        BigDecimal xp = player.getExperience().add(BigDecimal.valueOf(amount));
        int level = player.getLevel();
        boolean leveled = false;

        while (xp.compareTo(requiredFor(level)) >= 0) {
            xp = xp.subtract(requiredFor(level));
            level++;
            leveled = true;
        }
        try {
            var expField = Player.class.getDeclaredField("experience");
            var levelField = Player.class.getDeclaredField("level");
            expField.setAccessible(true);
            levelField.setAccessible(true);
            expField.set(player, xp);
            levelField.set(player, level);
        } catch (Exception ignore) {
        }

        if (leveled) updatePetSlotsUnlocked(player);
    }

    private BigDecimal requiredFor(int currentLevel) {
        return BASE_XP.multiply(INCREMENT.pow(Math.max(0, currentLevel - 1)));
    }

    private void updatePetSlotsUnlocked(Player player) {
        int lvl = player.getLevel();
        int slots = (lvl >= 30) ? 4 : (lvl >= 20) ? 3 : (lvl >= 10) ? 2 : (lvl >= 3) ? 1 : 0;
        int current = player.getPetSlotsUnlocked();
        if (slots > current) {
            try {
                var f = Player.class.getDeclaredField("petSlotsUnlocked");
                f.setAccessible(true);
                f.set(player, slots);
            } catch (Exception ignore) {
            }
        }
    }

    public record LevelView(int level, long experience, long requiredExperience, int petSlotsUnlocked) {
    }

    public LevelView toView(Player p) {
        long req = requiredFor(p.getLevel()).longValue();
        return new LevelView(p.getLevel(), p.getExperience().longValue(), req, p.getPetSlotsUnlocked());
    }
}
