package com.artemi.player.domain.repository;

import com.artemi.player.domain.model.Player;
import com.artemi.player.domain.model.PlayerId;
import java.util.*;

public interface PlayerRepository {
    Optional<Player> findById(PlayerId id);
    Optional<Player> findByUsernameIgnoreCase(String username);
    Player save(Player player);
    List<Player> topByPower(int limit);
    List<Player> topByPowerFiltered(String field, String value, int limit);
}