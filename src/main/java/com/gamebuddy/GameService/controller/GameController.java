package com.gamebuddy.GameService.controller;

import com.gamebuddy.GameService.dto.GameCreateDTO;
import com.gamebuddy.GameService.dto.GameViewDTO;
import com.gamebuddy.GameService.exception.results.DataResult;
import com.gamebuddy.GameService.exception.results.Result;
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
    public ResponseEntity<DataResult<GameViewDTO>> createGame(@RequestBody GameCreateDTO gameCreateDTO) {
        return new ResponseEntity<>(gameService.createGame(gameCreateDTO), HttpStatus.CREATED);
    }

    @Operation(summary = "Get game by ID",
            description = "Retrieves the game details for the given ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Game found"),
            @ApiResponse(responseCode = "404", description = "Game not found")
    })
    @GetMapping("/{gameId}")
    public ResponseEntity<DataResult<GameViewDTO>> getGameByGameId(
            @PathVariable String gameId) {
        return new ResponseEntity<>(gameService.getGameByGameId(gameId), HttpStatus.OK);
    }

    @Operation(summary = "Get all games",
            description = "Retrieves a list of all games.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Games retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No games found")
    })
    @GetMapping
    public ResponseEntity<DataResult<List<GameViewDTO>>> getAllGames() {
        return new ResponseEntity<>(gameService.getAllGames(), HttpStatus.OK);
    }

    @Operation(summary = "Delete game",
            description = "Deletes the game for the given ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Game deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Game not found")
    })
    @DeleteMapping("/{gameId}")
    public ResponseEntity<Result> deleteGame(@PathVariable String gameId) {
        return new ResponseEntity<>(gameService.deleteGame(gameId), HttpStatus.OK);
    }
}
