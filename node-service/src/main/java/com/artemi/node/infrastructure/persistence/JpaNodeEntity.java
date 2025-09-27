package com.artemi.node.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "node")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JpaNodeEntity {
    @Id
    @Column(name = "node_id")
    private String nodeId;
    private String playerId;
    @Column(name = "score", precision = 12, scale = 2)
    private BigDecimal score;
    private String country;
    private String city;
    private Instant timeWhenUpdated;
}
