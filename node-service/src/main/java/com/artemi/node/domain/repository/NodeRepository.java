package com.artemi.node.domain.repository;

import com.artemi.node.domain.model.Node;
import java.util.*;

public interface NodeRepository {
    Optional<Node> findById(String nodeId);
    Node save(Node node);
    List<String> nodeIdsForPlayer(String playerId);
}
