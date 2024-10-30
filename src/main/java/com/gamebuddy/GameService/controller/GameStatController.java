package com.gamebuddy.GameService.controller;

import com.gamebuddy.GameService.dto.GameStatCreateDTO;
import com.gamebuddy.GameService.dto.GameStatUpdateDTO;
import com.gamebuddy.GameService.dto.GameStatViewDTO;
import com.gamebuddy.GameService.exception.results.DataResult;
import com.gamebuddy.GameService.exception.results.Result;
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
    public ResponseEntity<DataResult<GameStatViewDTO>> createGameStat(@RequestBody GameStatCreateDTO gameStatCreateDTO) {
        return new ResponseEntity<>(gameStatService.createGameStat(gameStatCreateDTO), HttpStatus.CREATED);
    }

    @Operation(summary = "Update gameStat details",
            description = "Updates the gameStat details based on the provided gameStat ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "GameStat updated successfully"),
            @ApiResponse(responseCode = "404", description = "GameStat not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PutMapping("/{gameStatId}")
    public ResponseEntity<DataResult<GameStatViewDTO>> updateGameStat(
            @Parameter(description = "ID of the gameStat to be updated") @PathVariable String gameStatId,
            @RequestBody GameStatUpdateDTO gameStatUpdateDTO) {
        return new ResponseEntity<>(gameStatService.updateGameStat(gameStatId, gameStatUpdateDTO), HttpStatus.OK);
    }

    @Operation(summary = "Get gameStat by ID",
            description = "Retrieves gameStat details based on the provided gameStat ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "GameStat found"),
            @ApiResponse(responseCode = "404", description = "GameStat not found")
    })
    @GetMapping("/{gameStatId}")
    public ResponseEntity<DataResult<GameStatViewDTO>> getGameStatByGameStatId(
            @Parameter(description = "ID of the gameStat to be retrieved") @PathVariable String gameStatId) {
        return new ResponseEntity<>(gameStatService.getGameStatByGameStatId(gameStatId), HttpStatus.OK);
    }

    @Operation(summary = "Delete a gameStat",
            description = "Deletes the gameStat based on the provided gameStat ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "GameStat deleted successfully"),
            @ApiResponse(responseCode = "404", description = "GameStat not found")
    })
    @DeleteMapping("/{gameStatId}")
    public ResponseEntity<Result> deleteGameStat(@PathVariable String gameStatId) {
        return new ResponseEntity<>(gameStatService.deleteGameStat(gameStatId), HttpStatus.OK);
    }

    @Operation(summary = "Get gameStats by User ID",
            description = "Retrieves a list of gameStats based on the provided User ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "GameStats found"),
            @ApiResponse(responseCode = "404", description = "GameStats not found for the user")
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<DataResult<List<GameStatViewDTO>>> getGameStatsByUserId(
            @Parameter(description = "ID of the user whose gameStats are to be retrieved") @PathVariable String userId) {
        return new ResponseEntity<>(gameStatService.getGameStatsByUserId(userId), HttpStatus.OK);
    }

    @Operation(summary = "Get gameStats by ranks",
            description = "Retrieves a list of gameStats based on the provided ranks.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "GameStats found"),
            @ApiResponse(responseCode = "404", description = "GameStats not found for the ranks")
    })
    @GetMapping("/by-ranks")
    public ResponseEntity<DataResult<List<GameStatViewDTO>>> getGameStatsByRanks(@RequestParam List<String> ranks) {
        return new ResponseEntity<>(gameStatService.getGameStatsByRanks(ranks), HttpStatus.OK);
    }
}
