package com.artemi.player.infrastructure.web;

import com.artemi.player.application.service.ExperienceDomainService.LevelView;
import com.artemi.player.application.service.PlayerApplicationService;
import jakarta.validation.constraints.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequiredArgsConstructor
public class PlayerController {
    private final PlayerApplicationService app;

    @PostMapping("/player")
    public ResponseEntity<?> upsert(@RequestBody PlayerUpsert req) {
        var p = app.upsertPlayer(req.PlayerId, req.PlayfabPlayerId, req.Power, req.Username, req.Country, req.City);
        return ResponseEntity.ok(p);
    }

    @GetMapping("/player/{id}")
    public ResponseEntity<?> get(@PathVariable String id) {
        var p = app.getWithPets(id, List.of());
        return ResponseEntity.ok(p);
    }

    @PostMapping("/player/level/{id}/{xp}")
    public ResponseEntity<LevelView> addXp(@PathVariable String id, @PathVariable long xp) {
        return ResponseEntity.ok(app.addExperience(id, xp));
    }

    @GetMapping("/player/level/{id}")
    public ResponseEntity<LevelView> getLevel(@PathVariable String id) {
        return ResponseEntity.ok(app.getLevel(id));
    }

    @PostMapping("/player/assignPetToSlot")
    public ResponseEntity<?> assign(@RequestBody AssignPetToSlot req) {
        app.assignPetToSlot(req.PlayerId, req.SlotIndex, req.PetInstanceId);
        return ResponseEntity.ok(Map.of("message", "Pet assigned to slot"));
    }

    @PostMapping("/player/removePetFromSlot")
    public ResponseEntity<?> remove(@RequestBody RemovePetFromSlot req) {
        app.removePetFromSlot(req.PlayerId, req.SlotIndex);
        return ResponseEntity.ok(Map.of("message", "Pet removed from slot"));
    }

    @PostMapping("/player/unlockPetSkin")
    public ResponseEntity<?> unlock(@RequestBody UnlockPetSkin req) {
        app.unlockPetSkin(req.PlayerId, req.SkinId, req.PetType);
        return ResponseEntity.ok(Map.of("message", "Skin unlocked successfully"));
    }

    @GetMapping("/leaderboard")
    public ResponseEntity<?> top() {
        return ResponseEntity.ok(app.leaderboardTop(10));
    }

    @GetMapping("/leaderboard/{id}")
    public ResponseEntity<?> topPlusSelf(@PathVariable String id) {
        return ResponseEntity.ok(app.leaderboardByFilter(id, "all", 10));
    }

    @GetMapping("/leaderboard/{id}/{filter}")
    public ResponseEntity<?> topByFilter(@PathVariable String id, @PathVariable String filter) {
        return ResponseEntity.ok(app.leaderboardByFilter(id, filter, 10));
    }

    public record PlayerUpsert(
            @NotBlank String PlayerId, String PlayfabPlayerId, Integer Power,
            @NotBlank String Username, String Country, String City) {
    }

    public record AssignPetToSlot(@NotBlank String PlayerId, @Min(0) int SlotIndex,
                                  @NotBlank String PetInstanceId) {
    }

    public record RemovePetFromSlot(@NotBlank String PlayerId, @Min(0) int SlotIndex) {
    }

    public record UnlockPetSkin(@NotBlank String PlayerId, @Min(0) int SkinId,
                                @NotBlank String PetType) {
    }
}
