package com.gamebuddy.GameService.dto;

import lombok.Data;

import java.util.List;

@Data
public class GameCreateDTO {
    private String name;
    private String category;
    private List<String> rankSystem;
}
