package com.gamebuddy.GameService.dto;

import lombok.Data;

@Data
public class GameStatViewDTO {
    private String gameStatId;
    private String gameId;
    private String userId;
    private String gameRank;
}
