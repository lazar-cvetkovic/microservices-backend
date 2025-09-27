package com.artemi.node.infrastructure.web;

import com.artemi.node.application.service.NodeApplicationService;
import com.artemi.node.domain.model.Node;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.*;

@RestController
@RequiredArgsConstructor
public class NodeController {
    private final NodeApplicationService app;

    @PostMapping("/node")
    public ResponseEntity<Node> upsert(@RequestBody NodeRequest req) {
        var saved = app.upsertNode(req.NodeId, req.PlayerId, req.Score, req.Country, req.City, req.TimeWhenUpdated());
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/node/{id}")
    public ResponseEntity<Node> get(@PathVariable String id) {
        // If not found, create with reverse geocoding (omitted here). Stub: create empty.
        Node created = app.getNodeOrCreate(id, () ->
                Node.builder().nodeId(id).country(null).city(null).score(0).timeWhenUpdated(Instant.now()).build());
        return ResponseEntity.ok(created);
    }

    @PostMapping("/nodes")
    public ResponseEntity<?> batch(@RequestBody NodeIds req) {
        var list = new ArrayList<Node>();
        for (var nodeId : req.NodeIds) {
            var n = app.getNodeOrCreate(nodeId, () ->
                    Node.builder().nodeId(nodeId).country(null).city(null).score(0).timeWhenUpdated(Instant.now()).build());
            list.add(n);
        }
        return ResponseEntity.ok(Map.of("Nodes", list));
    }

    @DeleteMapping("/nodes")
    public ResponseEntity<?> wipe(@RequestBody Map<String, String> body) {
        // (add a real auth later). For now just 401 like your old code.
        return ResponseEntity.status(401).body("Unauthorized");
    }

    @GetMapping("/nodes/{playerId}")
    public ResponseEntity<?> nodeIds(@PathVariable String playerId) {
        var ids = app.nodeIdsForPlayer(playerId);
        return ResponseEntity.ok(Map.of("playerId", playerId, "nodeIds", ids));
    }

    public record NodeRequest(@NotBlank String NodeId, String PlayerId, double Score,
                              String Country, String City, Instant TimeWhenUpdated) {
    }

    public record NodeIds(List<String> NodeIds) {
    }
}
