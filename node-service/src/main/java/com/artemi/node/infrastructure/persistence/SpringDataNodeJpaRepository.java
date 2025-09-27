package com.artemi.node.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface SpringDataNodeJpaRepository extends JpaRepository<JpaNodeEntity, String> {
    List<JpaNodeEntity> findByPlayerId(String playerId);
}

