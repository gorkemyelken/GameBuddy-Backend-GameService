package com.gamebuddy.GameService.service;

import com.gamebuddy.GameService.dto.GameStatCreateDTO;
import com.gamebuddy.GameService.dto.GameStatUpdateDTO;
import com.gamebuddy.GameService.dto.GameStatViewDTO;
import com.gamebuddy.GameService.exception.GameNotFoundException;
import com.gamebuddy.GameService.exception.GameStatNotFoundException;
import com.gamebuddy.GameService.model.GameStat;
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
    private final ModelMapper modelMapper;

    @Autowired
    public GameStatService(GameStatRepository gameStatRepository, ModelMapper modelMapper) {
        this.gameStatRepository = gameStatRepository;
        this.modelMapper = modelMapper;
    }

    public GameStatViewDTO createGameStat(GameStatCreateDTO gameStatCreateDTO) {
        GameStat gameStat = modelMapper.map(gameStatCreateDTO, GameStat.class);
        gameStat.setId(UUID.randomUUID().toString());
        gameStatRepository.save(gameStat);
        return modelMapper.map(gameStat, GameStatViewDTO.class);
    }

    public GameStatViewDTO getGameStatById(String gameStatId) {
        GameStat gameStat = gameStatRepository.findById(gameStatId)
                .orElseThrow(() -> new GameStatNotFoundException("Game stat not found with id: " + gameStatId));
        return modelMapper.map(gameStat, GameStatViewDTO.class);
    }

    public void deleteGameStat(String gameStatId) {
        if (!gameStatRepository.existsById(gameStatId)) {
            throw new GameNotFoundException("Game not found with id: " + gameStatId);
        }
        gameStatRepository.deleteById(gameStatId);
    }

    public GameStatViewDTO updateGameStat(String gameStatId, GameStatUpdateDTO gameStatUpdateDTO) {
        GameStat gameStat = gameStatRepository.findById(gameStatId)
                .orElseThrow(() -> new GameStatNotFoundException("GameStat not found with id: " + gameStatId));

        if (gameStatUpdateDTO.getGameRank() != null) {
            gameStat.setGameRank(gameStatUpdateDTO.getGameRank());
        }

        gameStatRepository.save(gameStat);
        return modelMapper.map(gameStat, GameStatViewDTO.class);
    }

    public List<GameStatViewDTO> getGameStatsByUserId(String userId) {
        List<GameStat> gameStats = gameStatRepository.findByUserId(userId);
        return gameStats.stream()
                .map(gameStat -> modelMapper.map(gameStat, GameStatViewDTO.class))
                .collect(Collectors.toList());
    }

    public List<GameStatViewDTO> getGameStatsByRanks(List<String> ranks) {
        List<GameStat> gameStats = gameStatRepository.findByGameRanks(ranks);
        return gameStats.stream().map(gameStat -> modelMapper.map(gameStat, GameStatViewDTO.class))
                .collect(Collectors.toList());
    }
}
