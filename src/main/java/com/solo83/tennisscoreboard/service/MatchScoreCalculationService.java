package com.solo83.tennisscoreboard.service;
import com.solo83.tennisscoreboard.dto.MatchScoreModel;
import com.solo83.tennisscoreboard.dto.PlayerScore;
import com.solo83.tennisscoreboard.entity.Match;

import java.util.Objects;

public class MatchScoreCalculationService {

    private static MatchScoreCalculationService instance;

    private MatchScoreCalculationService() {
    }

    public static MatchScoreCalculationService getInstance() {
        if (instance == null) {
            instance = new MatchScoreCalculationService();
        }
        return instance;
    }

    public void calculateMatchScore(Integer playerId, MatchScoreModel matchScoreModel) {
        Match match = matchScoreModel.getMatch();
        Integer firstPlayerID = match.getFirstPlayer().getId();
        PlayerScore firstPlayerScore = matchScoreModel.getFirstPlayerScore();
        PlayerScore secondPlayerScore = matchScoreModel.getSecondPlayerScore();

        if (Objects.equals(playerId, firstPlayerID)) {
            firstPlayerScore.setPoints(firstPlayerScore.getPoints()+15);
        } else {
            secondPlayerScore.setPoints(secondPlayerScore.getPoints()+15);
        }
    }

}
