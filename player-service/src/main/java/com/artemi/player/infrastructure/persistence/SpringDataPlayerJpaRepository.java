package com.artemi.player.infrastructure.persistence;

import org.springframework.data.jpa.repository.*;

import java.util.*;

public interface SpringDataPlayerJpaRepository extends JpaRepository<JpaPlayerEntity, String> {
    Optional<JpaPlayerEntity> findByUsernameIgnoreCase(String username);

    List<JpaPlayerEntity> findTop10ByOrderByPowerDesc();

    List<JpaPlayerEntity> findTop10ByCountryOrderByPowerDesc(String country);

    List<JpaPlayerEntity> findTop10ByCityOrderByPowerDesc(String city);
}
