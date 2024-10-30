package com.gamebuddy.GameService.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GameStat {

    @Id
    @Column(updatable = false, nullable = false)
    private String gameStatId;

    private String gameId;

    private String userId;

    private String gameRank;
}
