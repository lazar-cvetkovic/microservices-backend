package com.artemi.node.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface SpringDataNodeJpaRepository extends JpaRepository<JpaNodeEntity, String> {
    List<JpaNodeEntity> findByPlayerId(String playerId);
}

