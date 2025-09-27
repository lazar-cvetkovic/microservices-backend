package com.artemi.player.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "player_assigned_pet_slot")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@IdClass(JpaAssignedPetSlotKey.class)
public class JpaAssignedPetSlotEntity {
    @Id
    @Column(name = "player_id")
    private String playerId;
    @Id
    @Column(name = "slot_index")
    private int slotIndex;
    private String petInstanceId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id", insertable = false, updatable = false)
    private JpaPlayerEntity player;
}

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class JpaAssignedPetSlotKey implements java.io.Serializable {
    private String playerId;
    private int slotIndex;
}
