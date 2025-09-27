package com.artemi.player.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PetSlot {
    private final int slotIndex;
    private final String petInstanceId;
}
