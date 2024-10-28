package com.gamebuddy.GameService.controller;

import com.gamebuddy.GameService.dto.GameStatCreateDTO;
import com.gamebuddy.GameService.dto.GameStatUpdateDTO;
import com.gamebuddy.GameService.dto.GameStatViewDTO;
import com.gamebuddy.GameService.service.GameStatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/gamestats")
@CrossOrigin
public class GameStatController {
    private final GameStatService gameStatService;

    public GameStatController(GameStatService gameStatService) {
        this.gameStatService = gameStatService;
    }

    @Operation(summary = "Create a new gameStat",
            description = "Creates a new gameStat and returns the created gameStat's details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "GameStat created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping("/create")
    public ResponseEntity<GameStatViewDTO> createGameStat(@RequestBody GameStatCreateDTO gameStatCreateDTO) {
        GameStatViewDTO createdGameStat = gameStatService.createGameStat(gameStatCreateDTO);
        return new ResponseEntity<>(createdGameStat, HttpStatus.CREATED);
    }

    @Operation(summary = "Update gameStat details",
            description = "Updates the gameStat details based on the provided gameStat ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "GameStat updated successfully"),
            @ApiResponse(responseCode = "404", description = "GameStat not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PutMapping("/{id}")
    public ResponseEntity<GameStatViewDTO> updateGameStat(
            @Parameter(description = "ID of the gameStat to be updated") @PathVariable String id,
            @RequestBody GameStatUpdateDTO gameStatUpdateDTO) {
        GameStatViewDTO updatedGameStat = gameStatService.updateGameStat(id, gameStatUpdateDTO);
        return ResponseEntity.ok(updatedGameStat);
    }

    @Operation(summary = "Get gameStat by ID",
            description = "Retrieves gameStat details based on the provided gameStat ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "GameStat found"),
            @ApiResponse(responseCode = "404", description = "GameStat not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<GameStatViewDTO> getGameStatById(
            @Parameter(description = "ID of the gameStat to be retrieved") @PathVariable String id) {
        GameStatViewDTO gameStatViewDTO = gameStatService.getGameStatById(id);
        return ResponseEntity.ok(gameStatViewDTO);
    }

    @Operation(summary = "Delete a gameStat",
            description = "Deletes the gameStat based on the provided gameStat ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "GameStat deleted successfully"),
            @ApiResponse(responseCode = "404", description = "GameStat not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGameStat(
            @Parameter(description = "ID of the gameStat to be deleted") @PathVariable String id) {
        gameStatService.deleteGameStat(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get gameStats by User ID",
            description = "Retrieves a list of gameStats based on the provided User ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "GameStats found"),
            @ApiResponse(responseCode = "404", description = "GameStats not found for the user")
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<GameStatViewDTO>> getGameStatsByUserId(
            @Parameter(description = "ID of the user whose gameStats are to be retrieved") @PathVariable String userId) {
        List<GameStatViewDTO> gameStatsViewDTOs = gameStatService.getGameStatsByUserId(userId);
        if (gameStatsViewDTOs.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(gameStatsViewDTOs);
    }

    @Operation(summary = "Get gameStats by ranks",
            description = "Retrieves a list of gameStats based on the provided ranks.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "GameStats found"),
            @ApiResponse(responseCode = "404", description = "GameStats not found for the ranks")
    })
    @GetMapping("/by-ranks")
    public ResponseEntity<List<GameStatViewDTO>> getGameStatsByRanks(@RequestParam List<String> ranks) {
        List<GameStatViewDTO> gameStatsViewDTOs = gameStatService.getGameStatsByRanks(ranks);
        return ResponseEntity.ok(gameStatsViewDTOs);
    }
}
