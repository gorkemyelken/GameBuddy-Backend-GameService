package com.gamebuddy.GameService.repository;

import com.gamebuddy.GameService.model.GameStat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GameStatRepository extends JpaRepository<GameStat, String> {
    List<GameStat> findByUserId(String userId);

    @Query("SELECT g FROM GameStat g WHERE g.gameRank IN :ranks")
    List<GameStat> findByGameRanks(@Param("ranks") List<String> ranks);
}

