package com.solo83.tennisscoreboard.service;

import com.solo83.tennisscoreboard.dto.OngoingMatch;
import com.solo83.tennisscoreboard.dto.PlayerScore;
import com.solo83.tennisscoreboard.entity.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MatchScoreCalculationServiceTest {

    private MatchScoreCalculationService matchScoreCalculationService;
    private OngoingMatch ongoingMatch;
    private PlayerScore firstPlayerScore;
    private PlayerScore secondPlayerScore;
    private Player firstPlayer;
    private Player secondPlayer;


    @BeforeEach
    void setUp(){
        matchScoreCalculationService = MatchScoreCalculationService.getInstance();

        firstPlayerScore = new PlayerScore();
        secondPlayerScore = new PlayerScore();
        firstPlayer = new Player("First Player");
        secondPlayer = new Player("Second Player");

        firstPlayer.setId(1);
        secondPlayer.setId(2);

        ongoingMatch = new OngoingMatch();
        ongoingMatch.setFirstPlayerScore(firstPlayerScore);
        ongoingMatch.setSecondPlayerScore(secondPlayerScore);
        ongoingMatch.setFirstPlayer(firstPlayer);
        ongoingMatch.setSecondPlayer(secondPlayer);


    }

    @Test
    void testCalculateMatchScoreRegularPlay() {
        firstPlayerScore.setPoints(30);
        secondPlayerScore.setPoints(15);
        matchScoreCalculationService.calculateMatchScore(1, ongoingMatch);

        assertEquals(40, firstPlayerScore.getPoints());
        assertEquals(15, secondPlayerScore.getPoints());
    }

    @Test
    void testCalculateMatchScoreDraw() {
        firstPlayerScore.setPoints(40);
        secondPlayerScore.setPoints(40);
        matchScoreCalculationService.calculateMatchScore(2, ongoingMatch);

        assertTrue(ongoingMatch.isDraw());
        assertEquals(0, firstPlayerScore.getPoints());
        assertEquals(0, secondPlayerScore.getPoints());
    }

    @Test
    void testCalculateMatchScoreWinGame() {
        firstPlayerScore.setPoints(40);
        secondPlayerScore.setPoints(0);

        matchScoreCalculationService.calculateMatchScore(1, ongoingMatch);

        assertEquals(1, firstPlayerScore.getGame());
        assertEquals(0, firstPlayerScore.getPoints());
        assertEquals(0, secondPlayerScore.getPoints());
    }

    @Test
    void testCheckMatchWinnerFirstPlayerWins() {
        firstPlayerScore.setSets(2);
        secondPlayerScore.setSets(1);

        assertTrue(matchScoreCalculationService.checkMatchWinner(firstPlayerScore, secondPlayerScore, ongoingMatch));
        assertEquals(ongoingMatch.getWinner(), firstPlayer);
    }

    @Test
    void testCheckMatchWinnerNoWinner() {
        firstPlayerScore.setSets(1);
        secondPlayerScore.setSets(1);

        assertFalse(matchScoreCalculationService.checkMatchWinner(firstPlayerScore, secondPlayerScore, ongoingMatch));
        assertNull(ongoingMatch.getWinner());
    }

    @Test
    void testHandleRegularPlayDraw() {
        firstPlayerScore.setPoints(40);
        secondPlayerScore.setPoints(40);

        matchScoreCalculationService.handleRegularPlay(1, 1, firstPlayerScore, secondPlayerScore, ongoingMatch);
        assertTrue(ongoingMatch.isDraw());
    }

    @Test
    void testHandleTieBreakPlay() {
        ongoingMatch.setTieBreak(true);
        firstPlayerScore.setPoints(5);
        secondPlayerScore.setPoints(4);

        matchScoreCalculationService.handleTieBreakPlay(1, 1, firstPlayerScore, secondPlayerScore, ongoingMatch);
        assertEquals(6, firstPlayerScore.getPoints());
    }

    @Test
    void testCheckSetWinner() {
        firstPlayerScore.setGame(6);
        secondPlayerScore.setGame(4);

        matchScoreCalculationService.checkSetWinner(firstPlayerScore, secondPlayerScore, ongoingMatch);
        assertEquals(1, firstPlayerScore.getSets());
        assertEquals(0, firstPlayerScore.getGame());
        assertEquals(0, secondPlayerScore.getGame());
    }

    @Test
    void testTieBreakStartsAtSixGamesEach() {
        firstPlayerScore.setGame(6);
        secondPlayerScore.setGame(6);

        matchScoreCalculationService.checkSetWinner(firstPlayerScore, secondPlayerScore, ongoingMatch);
        assertTrue(ongoingMatch.isTieBreak());
    }

}