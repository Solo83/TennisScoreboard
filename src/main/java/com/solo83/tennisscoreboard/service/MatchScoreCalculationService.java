package com.solo83.tennisscoreboard.service;

import com.solo83.tennisscoreboard.dto.OngoingMatch;
import com.solo83.tennisscoreboard.dto.PlayerScore;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Slf4j
public class MatchScoreCalculationService {

    private static MatchScoreCalculationService instance;
    private final List<Integer> TENNIS_POINTS = Arrays.asList(0, 15, 30, 40);
    private final int POINTS_DIFFERENCE = 2;

    private MatchScoreCalculationService() {
    }

    public static MatchScoreCalculationService getInstance() {
        if (instance == null) {
            instance = new MatchScoreCalculationService();
        }
        return instance;
    }

    public boolean calculateMatchScore(Integer playerId, OngoingMatch ongoingMatch) {

        Integer firstPlayerID = ongoingMatch.getFirstPlayer().getId();
        PlayerScore firstPlayerScore = ongoingMatch.getFirstPlayerScore();
        PlayerScore secondPlayerScore = ongoingMatch.getSecondPlayerScore();
        boolean isTieBreak = ongoingMatch.isTieBreak();
        boolean isAdvantage = ongoingMatch.isAdvantage();


        if (isTieBreak) {
            handleTieBreakPlay(playerId, firstPlayerID, firstPlayerScore, secondPlayerScore, ongoingMatch);
        } else if (isAdvantage) {
            handleAdvantagePlay(playerId, firstPlayerID, firstPlayerScore, secondPlayerScore, ongoingMatch);
        } else {
            handleRegularPlay(playerId, firstPlayerID, firstPlayerScore, secondPlayerScore, ongoingMatch);
        }
        checkSetWinner(firstPlayerScore, secondPlayerScore, ongoingMatch);

        if (checkMatchWinner(firstPlayerScore, secondPlayerScore, ongoingMatch)) {
            log.info("Match finished");
            return true;
        }
        return false;
    }

    boolean checkMatchWinner(PlayerScore firstPlayerScore, PlayerScore secondPlayerScore, OngoingMatch ongoingMatch) {
        int player1sets = firstPlayerScore.getSets();
        int player2sets = secondPlayerScore.getSets();

        int TOTAL_SETS = 3;
        if (player1sets == TOTAL_SETS / 2 + 1) {
            ongoingMatch.setWinner(ongoingMatch.getFirstPlayer());
            log.info("Winner is first player - {}", ongoingMatch.getFirstPlayer());
            return true;
        } else if (player2sets == TOTAL_SETS / 2 + 1) {
            ongoingMatch.setWinner(ongoingMatch.getSecondPlayer());
            log.info("Winner is second player - {}", ongoingMatch.getSecondPlayer());
            return true;
        }
        return false;
    }

    void handleRegularPlay(Integer playerId, Integer firstPlayerID, PlayerScore firstPlayerScore, PlayerScore secondPlayerScore, OngoingMatch ongoingMatch) {

        if (!ongoingMatch.isAdvantage()) {
            setNextPoint(playerId, firstPlayerID, firstPlayerScore, secondPlayerScore);
            checkAdvantageScore(firstPlayerScore, secondPlayerScore, ongoingMatch);
        }

        if (!TENNIS_POINTS.contains(firstPlayerScore.getPoints())) {
            firstPlayerScore.incrementGame();
            resetPoints(ongoingMatch);

        } else if (!TENNIS_POINTS.contains(secondPlayerScore.getPoints())) {
            secondPlayerScore.incrementGame();
            resetPoints(ongoingMatch);
        }

    }

    void checkAdvantageScore(PlayerScore firstPlayerScore, PlayerScore secondPlayerScore, OngoingMatch ongoingMatch) {
        int ADVANTAGE_POINT = 40;
        if (firstPlayerScore.getPoints() == ADVANTAGE_POINT && secondPlayerScore.getPoints() == ADVANTAGE_POINT) {
            handleAdvantage(ongoingMatch);
        }
    }

    private void handleAdvantagePlay(Integer playerId, Integer firstPlayerID, PlayerScore firstPlayerScore, PlayerScore secondPlayerScore, OngoingMatch ongoingMatch) {

        incrementPoints(playerId, firstPlayerID, firstPlayerScore, secondPlayerScore);
        int pointDifference = firstPlayerScore.getPoints() - secondPlayerScore.getPoints();

        if ((Math.abs(pointDifference) == 0)) {
            invertAndDecrementPoints(playerId, firstPlayerID, firstPlayerScore, secondPlayerScore);
        }
        if (Math.abs(pointDifference) == POINTS_DIFFERENCE) {
            if (pointDifference > 0) {
                firstPlayerScore.incrementGame();

            } else {
                secondPlayerScore.incrementGame();
            }
            ongoingMatch.setAdvantage(false);
            resetPoints(ongoingMatch);
        }
    }

    void checkSetWinner(PlayerScore firstPlayerScore, PlayerScore secondPlayerScore, OngoingMatch ongoingMatch) {
        int player1games = firstPlayerScore.getGame();
        int player2games = secondPlayerScore.getGame();
        int gamesDifference = player1games - player2games;
        boolean isTieBreak = ongoingMatch.isTieBreak();

        int TOTAL_GAMES = 6;
        if (!isTieBreak && player1games == TOTAL_GAMES && player2games == TOTAL_GAMES) {
            handleTieBreak(ongoingMatch);
        } else if (Math.abs(gamesDifference) >= POINTS_DIFFERENCE && (player1games >= TOTAL_GAMES || player2games >= TOTAL_GAMES)) {
            if (gamesDifference > 0) {
                firstPlayerScore.incrementSet();
                saveGames(firstPlayerScore,secondPlayerScore,player1games,player2games);
                log.info("First player won SET, score saved");
            } else {
                secondPlayerScore.incrementSet();
                saveGames(firstPlayerScore,secondPlayerScore,player1games,player2games);
                log.info("Second player won SET, score saved");
            }
            resetGames(ongoingMatch);
        }
    }

    void handleTieBreakPlay(Integer playerId, Integer firstPlayerID, PlayerScore firstPlayerScore, PlayerScore secondPlayerScore, OngoingMatch ongoingMatch) {

        incrementPoints(playerId, firstPlayerID, firstPlayerScore, secondPlayerScore);

        int player1points = firstPlayerScore.getPoints();
        int player2points = secondPlayerScore.getPoints();
        int pointDifference = player1points - player2points;

        int TIE_BREAK_POINTS = 7;
        if (Math.abs(pointDifference) >= POINTS_DIFFERENCE && (player1points >= TIE_BREAK_POINTS || player2points >= TIE_BREAK_POINTS)) {
            if (pointDifference > 0) {
                firstPlayerScore.incrementSet();
                saveGames(firstPlayerScore,secondPlayerScore,TIE_BREAK_POINTS,secondPlayerScore.getGame());
                log.info("First player won TIE_BREAK, score saved");
            } else {
                secondPlayerScore.incrementSet();
                saveGames(firstPlayerScore,secondPlayerScore,firstPlayerScore.getGame(),TIE_BREAK_POINTS);
                log.info("Second player won TIE_BREAK, score saved");
            }
            resetGames(ongoingMatch);
            resetPoints(ongoingMatch);
            ongoingMatch.setTieBreak(false);
        }
    }

    private void setNextPoint(Integer playerId, Integer firstPlayerID, PlayerScore firstPlayerScore, PlayerScore secondPlayerScore) {
        if (Objects.equals(playerId, firstPlayerID)) {
            firstPlayerScore.nextPoint(TENNIS_POINTS);
        } else {
            secondPlayerScore.nextPoint(TENNIS_POINTS);
        }
    }

    private void incrementPoints(Integer playerId, Integer firstPlayerID, PlayerScore firstPlayerScore, PlayerScore secondPlayerScore) {
        if (Objects.equals(playerId, firstPlayerID)) {
            firstPlayerScore.incrementPoints();
        } else {
            secondPlayerScore.incrementPoints();
        }
    }

    private void invertAndDecrementPoints(Integer playerId, Integer firstPlayerID, PlayerScore firstPlayerScore, PlayerScore secondPlayerScore) {
        if (Objects.equals(playerId, firstPlayerID)) {
            secondPlayerScore.decrementPoints();
            firstPlayerScore.resetPoints();
        } else {
            firstPlayerScore.decrementPoints();
            secondPlayerScore.resetPoints();
        }
    }

    private void resetPoints(OngoingMatch ongoingMatch) {
        ongoingMatch.getFirstPlayerScore().resetPoints();
        ongoingMatch.getSecondPlayerScore().resetPoints();
    }

    private void resetGames(OngoingMatch ongoingMatch) {
        ongoingMatch.getFirstPlayerScore().resetGames();
        ongoingMatch.getSecondPlayerScore().resetGames();
    }

    private void handleAdvantage(OngoingMatch ongoingMatch) {
        ongoingMatch.setAdvantage(true);
        resetPoints(ongoingMatch);
    }

    private void handleTieBreak(OngoingMatch ongoingMatch) {
        ongoingMatch.setTieBreak(true);
        resetPoints(ongoingMatch);
    }

    private void saveGames(PlayerScore firstPlayerScore, PlayerScore secondPlayerScore, int player1games, int player2games) {
        firstPlayerScore.save(player1games);
        secondPlayerScore.save(player2games);
    }

}
