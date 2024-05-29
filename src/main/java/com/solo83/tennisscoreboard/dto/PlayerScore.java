package com.solo83.tennisscoreboard.dto;

import lombok.Data;

@Data
public class PlayerScore {
    private String playerName;
    private int game;
    private int points;
    private int sets;
}
