package com.artemi.node.infrastructure.persistence;

import com.artemi.node.domain.model.Node;
import com.artemi.node.domain.repository.NodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.time.Instant;

@Repository
@RequiredArgsConstructor
public class NodeRepositoryAdapter implements NodeRepository {
    private final SpringDataNodeJpaRepository jpa;

    @Override
    public Optional<Node> findById(String nodeId) {
        return jpa.findById(nodeId).map(this::toDomain);
    }

    @Override
    public Node save(Node n) {
        var e = new JpaNodeEntity(n.getNodeId(), n.getPlayerId(), n.getScore(), n.getCountry(),
                n.getCity(), n.getTimeWhenUpdated());
        return toDomain(jpa.save(e));
    }

    @Override
    public List<String> nodeIdsForPlayer(String playerId) {
        return jpa.findByPlayerId(playerId).stream().map(JpaNodeEntity::getNodeId).toList();
    }

    private Node toDomain(JpaNodeEntity e) {
        return Node.builder()
                .nodeId(e.getNodeId())
                .playerId(e.getPlayerId())
                .score(e.getScore())
                .country(e.getCountry())
                .city(e.getCity())
                .timeWhenUpdated(e.getTimeWhenUpdated() != null ? e.getTimeWhenUpdated() : Instant.now())
                .build();
    }
}
