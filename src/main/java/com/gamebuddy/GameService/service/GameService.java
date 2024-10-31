package com.gamebuddy.GameService.service;

import com.gamebuddy.GameService.dto.GameCreateDTO;
import com.gamebuddy.GameService.dto.GameViewDTO;
import com.gamebuddy.GameService.exception.results.*;
import com.gamebuddy.GameService.model.Game;
import com.gamebuddy.GameService.repository.GameRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class GameService {
    private final GameRepository gameRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public GameService(GameRepository gameRepository, ModelMapper modelMapper) {
        this.gameRepository = gameRepository;
        this.modelMapper = modelMapper;
    }

    public DataResult<GameViewDTO> createGame(GameCreateDTO gameCreateDTO) {
        Game game = modelMapper.map(gameCreateDTO, Game.class);
        game.setGameId(UUID.randomUUID().toString());
        gameRepository.save(game);
        GameViewDTO gameViewDTO = modelMapper.map(game, GameViewDTO.class);
        return new SuccessDataResult<>(gameViewDTO, "Game created successfully.");
    }

    public DataResult<GameViewDTO> getGameByGameId(String gameId) {
        if(!checkIfGameIdExists(gameId)){
            return new ErrorDataResult<>("Game not found.");
        }
        Game game = gameRepository.findByGameId(gameId);
        GameViewDTO gameViewDTO = modelMapper.map(game, GameViewDTO.class);
        return new SuccessDataResult<>(gameViewDTO, "Game found successfully.");
    }

    public Result deleteGame(String gameId) {
        if(!checkIfGameIdExists(gameId)){
            return new ErrorResult("Game not found.");
        }
        gameRepository.deleteById(gameId);
        return new SuccessResult("Game deleted " + gameId);
    }

    public DataResult<List<GameViewDTO>> getAllGames() {
        Sort sort = Sort.by(Sort.Direction.DESC,"name");
        List<Game> games = gameRepository.findAll(sort);
        if(games.isEmpty()){
            return new ErrorDataResult<>("Games not found.");
        }
        List<GameViewDTO> gameViewDTOs = games.stream()
                .map(game -> modelMapper.map(game, GameViewDTO.class))
                .collect(Collectors.toList());
        return new SuccessDataResult<>(gameViewDTOs, "All games retrieved successfully.");
    }

    private boolean checkIfGameIdExists(String gameId) {
        return this.gameRepository.existsByGameId(gameId);
    }
}
