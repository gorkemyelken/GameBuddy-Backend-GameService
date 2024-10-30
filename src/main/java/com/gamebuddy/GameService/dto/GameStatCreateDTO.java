package com.gamebuddy.GameService.dto;

import lombok.Data;

@Data
public class GameStatCreateDTO {
    private String gameId;
    private String userId;
    private String userName;
    private String gameRank;
}
