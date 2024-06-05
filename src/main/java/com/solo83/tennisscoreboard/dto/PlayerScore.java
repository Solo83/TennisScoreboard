package com.solo83.tennisscoreboard.dto;

import lombok.Data;

import java.util.ArrayList;

@Data
public class PlayerScore {
    private int game;
    private int points;
    private int sets;
    private ArrayList<Integer> gameScores =  new ArrayList<>();

    public void save(Integer gameValue) {
        gameScores.add(gameValue);
    }



}
