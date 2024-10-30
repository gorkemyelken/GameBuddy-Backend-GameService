package com.gamebuddy.GameService.repository;

import com.gamebuddy.GameService.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, String> {
    Game findByGameId(String gameId);

    boolean existsByGameId(String gameId);
}
