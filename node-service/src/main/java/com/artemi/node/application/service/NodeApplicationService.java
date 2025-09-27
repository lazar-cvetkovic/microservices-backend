package com.artemi.node.application.service;

import com.artemi.node.domain.model.Node;
import com.artemi.node.domain.repository.NodeRepository;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.*;

@RequiredArgsConstructor
public class NodeApplicationService {
    private final NodeRepository repo;

    public Node upsertNode(String nodeId, String playerId, double score, String country, String city, Instant when) {
        var n = repo.findById(nodeId).orElseGet(() ->
                Node.builder().nodeId(nodeId).country(country).city(city).score(0).timeWhenUpdated(Instant.now()).build());
        n.capture(playerId, score, country, city, when != null ? when : Instant.now());
        return repo.save(n);
    }

    public Node getNodeOrCreate(String nodeId, java.util.function.Supplier<Node> creator) {
        return repo.findById(nodeId).orElseGet(() -> repo.save(creator.get()));
    }

    public List<String> nodeIdsForPlayer(String playerId) {
        return repo.nodeIdsForPlayer(playerId);
    }
}
