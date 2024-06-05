package com.solo83.tennisscoreboard.service;

import com.solo83.tennisscoreboard.dto.MatchScoreModel;
import com.solo83.tennisscoreboard.dto.PlayerScore;
import com.solo83.tennisscoreboard.entity.Match;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class MatchScoreCalculationService {

    private static MatchScoreCalculationService instance;

    private final List<Integer> TENNIS_POINTS = Arrays.asList(0, 15, 30, 40, -1);
    private final int POINTS_DIFFERENCE = 2;
    private boolean isTieBreak = false;
    private boolean isDraw = false;

    private MatchScoreCalculationService() {
    }

    public static MatchScoreCalculationService getInstance() {
        if (instance == null) {
            instance = new MatchScoreCalculationService();
        }
        return instance;
    }


    public boolean calculateMatchScore(Integer playerId, MatchScoreModel matchScoreModel) {
        Match match = matchScoreModel.getMatch();
        Integer firstPlayerID = match.getFirstPlayer().getId();
        PlayerScore firstPlayerScore = matchScoreModel.getFirstPlayerScore();
        PlayerScore secondPlayerScore = matchScoreModel.getSecondPlayerScore();

        if (checkMatchWinner(firstPlayerScore, secondPlayerScore, matchScoreModel)) {
            log.info("Match finished");
            return true;
        }

        if (isTieBreak) {
            handleTieBreakPlay(playerId, firstPlayerID, firstPlayerScore, secondPlayerScore, matchScoreModel);
        } else if (isDraw) {
            handleDrawPlay(playerId, firstPlayerID, firstPlayerScore, secondPlayerScore, matchScoreModel);
        } else {
            handleRegularPlay(playerId, firstPlayerID, firstPlayerScore, secondPlayerScore, matchScoreModel);
        }
        checkSetWinner(firstPlayerScore, secondPlayerScore, matchScoreModel);
        return false;
    }

    boolean checkMatchWinner(PlayerScore firstPlayerScore, PlayerScore secondPlayerScore, MatchScoreModel matchScoreModel) {
        int player1sets = firstPlayerScore.getSets();
        int player2sets = secondPlayerScore.getSets();
        Match match = matchScoreModel.getMatch();

        int TOTAL_SETS = 3;
        if (player1sets == TOTAL_SETS / 2 + 1) {
            match.setWinner(match.getFirstPlayer());
            log.info("Winner is first player - {}", match.getFirstPlayer());
            return true;
        } else if (player2sets == TOTAL_SETS / 2 + 1) {
            match.setWinner(match.getSecondPlayer());
            log.info("Winner is second player - {}", match.getSecondPlayer());
            return true;
        }
        return false;
    }

    void handleRegularPlay(Integer playerId, Integer firstPlayerID, PlayerScore firstPlayerScore, PlayerScore secondPlayerScore, MatchScoreModel matchScoreModel) {
        updatePoints(playerId, firstPlayerID, firstPlayerScore, secondPlayerScore);
        int DRAW_POINT = 40;
        if (firstPlayerScore.getPoints() == DRAW_POINT && secondPlayerScore.getPoints() == DRAW_POINT) {
            handleDraw(matchScoreModel);
        } else if (TENNIS_POINTS.indexOf(firstPlayerScore.getPoints()) == TENNIS_POINTS.size() - 1) {
            firstPlayerScore.setGame(firstPlayerScore.getGame() + 1);
            setZeroPoints(matchScoreModel);
        } else if (TENNIS_POINTS.indexOf(secondPlayerScore.getPoints()) == TENNIS_POINTS.size() - 1) {
            secondPlayerScore.setGame(secondPlayerScore.getGame() + 1);
            setZeroPoints(matchScoreModel);
        }
    }

    private void handleDrawPlay(Integer playerId, Integer firstPlayerID, PlayerScore firstPlayerScore, PlayerScore secondPlayerScore, MatchScoreModel matchScoreModel) {
        incrementPoints(playerId, firstPlayerID, firstPlayerScore, secondPlayerScore);

        int pointDifference = firstPlayerScore.getPoints() - secondPlayerScore.getPoints();
        if (Math.abs(pointDifference) == POINTS_DIFFERENCE) {
            if (pointDifference > 0) {
                firstPlayerScore.setGame(firstPlayerScore.getGame() + 1);
            } else {
                secondPlayerScore.setGame(secondPlayerScore.getGame() + 1);
            }
            setZeroPoints(matchScoreModel);
            isDraw = false;
            matchScoreModel.setDraw(false);
        }
    }

    void checkSetWinner(PlayerScore firstPlayerScore, PlayerScore secondPlayerScore, MatchScoreModel matchScoreModel) {
        int player1games = firstPlayerScore.getGame();
        int player2games = secondPlayerScore.getGame();
        int gamesDifference = player1games - player2games;

        int TOTAL_GAMES = 6;
        if (!isTieBreak && player1games == TOTAL_GAMES && player2games == TOTAL_GAMES) {
            handleTieBreak(matchScoreModel);
        } else if (Math.abs(gamesDifference) >= POINTS_DIFFERENCE && (player1games >= TOTAL_GAMES || player2games >= TOTAL_GAMES)) {
            if (gamesDifference > 0) {
                firstPlayerScore.setSets(firstPlayerScore.getSets() + 1);
                firstPlayerScore.save(player1games);
                secondPlayerScore.save(player2games);
                log.info("First player won SET, score saved");
            } else {
                secondPlayerScore.setSets(secondPlayerScore.getSets() + 1);
                secondPlayerScore.save(player2games);
                firstPlayerScore.save(player1games);
                log.info("Second player won SET, score saved");
            }
            setZeroGames(matchScoreModel);
        }
    }

    void handleTieBreakPlay(Integer playerId, Integer firstPlayerID, PlayerScore firstPlayerScore, PlayerScore secondPlayerScore, MatchScoreModel matchScoreModel) {
        incrementPoints(playerId, firstPlayerID, firstPlayerScore, secondPlayerScore);

        int player1points = firstPlayerScore.getPoints();
        int player2points = secondPlayerScore.getPoints();
        int pointDifference = player1points - player2points;

        int TIE_BREAK_POINTS = 7;
        if (Math.abs(pointDifference) >= POINTS_DIFFERENCE && (player1points >= TIE_BREAK_POINTS || player2points >= TIE_BREAK_POINTS)) {
            if (pointDifference > 0) {
                firstPlayerScore.setSets(firstPlayerScore.getSets() + 1);
                firstPlayerScore.save(TIE_BREAK_POINTS);
                secondPlayerScore.save(secondPlayerScore.getGame());
            } else {
                secondPlayerScore.setSets(secondPlayerScore.getSets() + 1);
                secondPlayerScore.save(TIE_BREAK_POINTS);
                firstPlayerScore.save(firstPlayerScore.getGame());
            }
            setZeroGames(matchScoreModel);
            setZeroPoints(matchScoreModel);
            isTieBreak = false;
            matchScoreModel.setTieBreak(false);
        }
    }

    private void updatePoints(Integer playerId, Integer firstPlayerID, PlayerScore firstPlayerScore, PlayerScore secondPlayerScore) {
        int updatedPoints;
        if (Objects.equals(playerId, firstPlayerID)) {
            updatedPoints = increaseGamePoints(firstPlayerScore.getPoints());
            firstPlayerScore.setPoints(updatedPoints);
        } else {
            updatedPoints = increaseGamePoints(secondPlayerScore.getPoints());
            secondPlayerScore.setPoints(updatedPoints);
        }
    }

    private void incrementPoints(Integer playerId, Integer firstPlayerID, PlayerScore firstPlayerScore, PlayerScore secondPlayerScore) {
        if (Objects.equals(playerId, firstPlayerID)) {
            int newPoints = firstPlayerScore.getPoints() + 1;
            firstPlayerScore.setPoints(newPoints);
        } else {
            int newPoints = secondPlayerScore.getPoints() + 1;
            secondPlayerScore.setPoints(newPoints);
        }
    }

    private int increaseGamePoints(int playerPoints) {
        int indexOfIncreased = TENNIS_POINTS.indexOf(playerPoints) + 1;
        return (indexOfIncreased < TENNIS_POINTS.size()) ? TENNIS_POINTS.get(indexOfIncreased) : 0;
    }

    private void setZeroPoints(MatchScoreModel matchScoreModel) {
        matchScoreModel.getFirstPlayerScore().setPoints(0);
        matchScoreModel.getSecondPlayerScore().setPoints(0);
    }

    private void setZeroGames(MatchScoreModel matchScoreModel) {
        matchScoreModel.getFirstPlayerScore().setGame(0);
        matchScoreModel.getSecondPlayerScore().setGame(0);
    }

    private void handleDraw(MatchScoreModel matchScoreModel) {
        isDraw = true;
        matchScoreModel.setDraw(true);
        setZeroPoints(matchScoreModel);
    }

    private void handleTieBreak(MatchScoreModel matchScoreModel) {
        isTieBreak = true;
        matchScoreModel.setTieBreak(true);
        setZeroPoints(matchScoreModel);
    }



}
