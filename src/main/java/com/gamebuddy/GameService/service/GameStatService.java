package com.gamebuddy.GameService.service;

import com.gamebuddy.GameService.dto.GameStatCreateDTO;
import com.gamebuddy.GameService.dto.GameStatUpdateDTO;
import com.gamebuddy.GameService.dto.GameStatViewDTO;
import com.gamebuddy.GameService.exception.results.*;
import com.gamebuddy.GameService.model.Game;
import com.gamebuddy.GameService.model.GameStat;
import com.gamebuddy.GameService.repository.GameRepository;
import com.gamebuddy.GameService.repository.GameStatRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class GameStatService {
    private final GameStatRepository gameStatRepository;

    private final GameRepository gameRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public GameStatService(GameStatRepository gameStatRepository, GameRepository gameRepository, ModelMapper modelMapper) {
        this.gameStatRepository = gameStatRepository;
        this.gameRepository = gameRepository;
        this.modelMapper = modelMapper;
    }

    public DataResult<GameStatViewDTO> createGameStat(GameStatCreateDTO gameStatCreateDTO) {
        GameStat gameStat = modelMapper.map(gameStatCreateDTO, GameStat.class);
        gameStat.setGameStatId(UUID.randomUUID().toString());
        Game game = gameRepository.findByGameId(gameStatCreateDTO.getGameId());
        gameStat.setGameName(game.getName());
        gameStat.setUserName(gameStatCreateDTO.getUserName());
        gameStatRepository.save(gameStat);
        GameStatViewDTO gameStatViewDTO = modelMapper.map(gameStat, GameStatViewDTO.class);
        return new SuccessDataResult<>(gameStatViewDTO, "Game stat created successfully.");
    }

    public DataResult<GameStatViewDTO> getGameStatByGameStatId(String gameStatId) {
        if(!checkIfGameStatIdExists(gameStatId)){
            return new ErrorDataResult<>("GameStat not found.");
        }
        GameStat gameStat = gameStatRepository.findByGameStatId(gameStatId);
        GameStatViewDTO gameStatViewDTO = modelMapper.map(gameStat, GameStatViewDTO.class);
        return new SuccessDataResult<>(gameStatViewDTO, "Game stat found successfully.");
    }

    public Result deleteGameStat(String gameStatId) {
        if(!checkIfGameStatIdExists(gameStatId)){
            return new ErrorResult("Game stat not found.");
        }
        gameStatRepository.deleteById(gameStatId);
        return new SuccessResult("Game stat deleted " + gameStatId);
    }

    public DataResult<GameStatViewDTO> updateGameStat(String gameStatId, GameStatUpdateDTO gameStatUpdateDTO) {
        if(!checkIfGameStatIdExists(gameStatId)){
            return new ErrorDataResult<>("Game stat not found.");
        }
        GameStat gameStat = gameStatRepository.findByGameStatId(gameStatId);

        if (gameStatUpdateDTO.getGameRank() != null) {
            gameStat.setGameRank(gameStatUpdateDTO.getGameRank());
        }

        gameStatRepository.save(gameStat);
        GameStatViewDTO gameStatViewDTO = modelMapper.map(gameStat, GameStatViewDTO.class);
        return new SuccessDataResult<>(gameStatViewDTO, "Game stat updated successfully.");
    }

    public DataResult<List<GameStatViewDTO>> getGameStatsByUserId(String userId) {
        List<GameStat> gameStats = gameStatRepository.findByUserId(userId);
        List<GameStatViewDTO> gameStatViewDTOs =  gameStats.stream()
                .map(gameStat -> modelMapper.map(gameStat, GameStatViewDTO.class))
                .collect(Collectors.toList());
        return new SuccessDataResult<>(gameStatViewDTOs, "Game stats retrieved by userId.");
    }

    public DataResult<List<GameStatViewDTO>> getGameStatsByRanks(List<String> ranks) {
        List<GameStat> gameStats = gameStatRepository.findByGameRanks(ranks);
        List<GameStatViewDTO> gameStatViewDTOs = gameStats.stream().map(gameStat -> modelMapper.map(gameStat, GameStatViewDTO.class))
                .collect(Collectors.toList());
        return new SuccessDataResult<>(gameStatViewDTOs, "Game stats retrieved by ranks.");
    }

    private boolean checkIfGameStatIdExists(String gameStatId) {
        return this.gameStatRepository.existsByGameStatId(gameStatId);
    }
}
