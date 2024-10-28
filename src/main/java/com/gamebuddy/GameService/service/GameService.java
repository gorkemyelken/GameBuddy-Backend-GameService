package com.gamebuddy.GameService.service;

import com.gamebuddy.GameService.dto.GameCreateDTO;
import com.gamebuddy.GameService.dto.GameViewDTO;
import com.gamebuddy.GameService.exception.GameNotFoundException;
import com.gamebuddy.GameService.model.Game;
import com.gamebuddy.GameService.repository.GameRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GameService {
    private final GameRepository gameRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public GameService(GameRepository gameRepository, ModelMapper modelMapper) {
        this.gameRepository = gameRepository;
        this.modelMapper = modelMapper;
    }

    public GameViewDTO createGame(GameCreateDTO gameCreateDTO) {
        Game game = modelMapper.map(gameCreateDTO, Game.class);
        game.setId(UUID.randomUUID().toString());
        gameRepository.save(game);
        return modelMapper.map(game, GameViewDTO.class);
    }

    public GameViewDTO getGameById(String gameId) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new GameNotFoundException("Game not found with id: " + gameId));
        return modelMapper.map(game, GameViewDTO.class);
    }

    public void deleteGame(String gameId) {
        if (!gameRepository.existsById(gameId)) {
            throw new GameNotFoundException("Game not found with id: " + gameId);
        }
        gameRepository.deleteById(gameId);
    }
}
