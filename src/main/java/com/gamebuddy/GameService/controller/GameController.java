package com.gamebuddy.GameService.controller;

import com.gamebuddy.GameService.dto.GameCreateDTO;
import com.gamebuddy.GameService.dto.GameViewDTO;
import com.gamebuddy.GameService.service.GameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/games")
@CrossOrigin
public class GameController {
    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @Operation(summary = "Create a new game",
            description = "Creates a new game and returns the created game details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Game created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    public ResponseEntity<GameViewDTO> createGame(@RequestBody GameCreateDTO gameCreateDTO) {
        GameViewDTO createdGame = gameService.createGame(gameCreateDTO);
        return new ResponseEntity<>(createdGame, HttpStatus.CREATED);
    }

    @Operation(summary = "Get game by ID",
            description = "Retrieves the game details for the given ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Game found"),
            @ApiResponse(responseCode = "404", description = "Game not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<GameViewDTO> getGameById(
            @PathVariable String id) {
        GameViewDTO gameViewDTO = gameService.getGameById(id);
        return ResponseEntity.ok(gameViewDTO);
    }

    @Operation(summary = "Get all games",
            description = "Retrieves a list of all games.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Games retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No games found")
    })
    @GetMapping
    public ResponseEntity<List<GameViewDTO>> getAllGames() {
        List<GameViewDTO> games = gameService.getAllGames();
        return ResponseEntity.ok(games);
    }

    @Operation(summary = "Delete game",
            description = "Deletes the game for the given ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Game deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Game not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGame(@PathVariable String id) {
        gameService.deleteGame(id);
        return ResponseEntity.noContent().build();
    }
}
