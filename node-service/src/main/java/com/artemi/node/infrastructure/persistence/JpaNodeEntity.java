package com.artemi.node.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.*;

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
    private double score;
    private String country;
    private String city;
    private Instant timeWhenUpdated;
}