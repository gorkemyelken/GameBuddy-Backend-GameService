package com.gamebuddy.GameService.dto;

import lombok.Data;

@Data
public class GameStatViewDTO {
    private String gameStatId;
    private String gameId;
    private String gameName;
    private String userId;
    private String userName;
    private String gameRank;
}
