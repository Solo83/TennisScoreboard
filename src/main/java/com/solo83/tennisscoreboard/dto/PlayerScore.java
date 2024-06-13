package com.solo83.tennisscoreboard.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PlayerScore {
    private int game;
    private int points;
    private int sets;
    private ArrayList<Integer> gameScores =  new ArrayList<>();

    public void save(Integer gameValue) {
        gameScores.add(gameValue);
    }

    public void resetPoints() {
        this.points = 0;
    }

    public void resetGames() {
        this.game = 0;
    }

    public void incrementGame() {
        this.game++;
    }

    public void incrementSet() {
        this.sets++;
    }

    public void incrementPoints() {
        this.points++;
    }

    public void decrementPoints() {
        this.points--;

    }

    public void nextPoint(List<Integer> tennisPoints) {
        int updatedPointsIndex = tennisPoints.indexOf(this.points) + 1;
        this.points = (updatedPointsIndex < tennisPoints.size()) ? tennisPoints.get(updatedPointsIndex) : -1;
    }



}
