package com.artemi.player.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UnlockedPetSkin {
    private final int skinId;
    private final String petType;
}