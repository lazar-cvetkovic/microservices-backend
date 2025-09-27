package com.artemi.player.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "player_unlocked_pet_skin")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@IdClass(JpaUnlockedPetSkinKey.class)
public class JpaUnlockedPetSkinEntity {
    @Id
    @Column(name = "player_id")
    private String playerId;
    @Id
    @Column(name = "skin_id")
    private int skinId;
    @Id
    @Column(name = "pet_type")
    private String petType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id", insertable = false, updatable = false)
    private JpaPlayerEntity player;
}

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class JpaUnlockedPetSkinKey implements java.io.Serializable {
    private String playerId;
    private int skinId;
    private String petType;
}
