package com.solo83.tennisscoreboard.service;

import com.solo83.tennisscoreboard.dto.MatchScoreModel;
import com.solo83.tennisscoreboard.dto.PlayerScore;
import com.solo83.tennisscoreboard.entity.Match;
import com.solo83.tennisscoreboard.entity.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MatchScoreCalculationServiceTest {

    private MatchScoreCalculationService matchScoreCalculationService;
    private MatchScoreModel matchScoreModel;
    private PlayerScore firstPlayerScore;
    private PlayerScore secondPlayerScore;
    private Match match;
    private Player firstPlayer;
    private Player secondPlayer;

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        matchScoreCalculationService = MatchScoreCalculationService.getInstance();
        Field isTieBreak = MatchScoreCalculationService.class.getDeclaredField("isTieBreak");
        Field isDraw = MatchScoreCalculationService.class.getDeclaredField("isDraw");
        isTieBreak.setAccessible(true);
        isTieBreak.set(matchScoreCalculationService,false);
        isDraw.setAccessible(true);
        isDraw.set(matchScoreCalculationService,false);

        firstPlayerScore = new PlayerScore();
        secondPlayerScore = new PlayerScore();
        firstPlayer = mock(Player.class);
        secondPlayer = mock(Player.class);
        match = mock(Match.class);

        when(firstPlayer.getId()).thenReturn(1);
        when(secondPlayer.getId()).thenReturn(2);
        when(match.getFirstPlayer()).thenReturn(firstPlayer);
        when(match.getSecondPlayer()).thenReturn(secondPlayer);

        matchScoreModel = new MatchScoreModel();
        matchScoreModel.setFirstPlayerScore(firstPlayerScore);
        matchScoreModel.setSecondPlayerScore(secondPlayerScore);
        matchScoreModel.setMatch(match);
    }

    @Test
    void testCalculateMatchScoreRegularPlay() {
        firstPlayerScore.setPoints(30);
        secondPlayerScore.setPoints(15);
        matchScoreCalculationService.calculateMatchScore(1, matchScoreModel);

        assertEquals(40, firstPlayerScore.getPoints());
        assertEquals(15, secondPlayerScore.getPoints());
    }

    @Test
    void testCalculateMatchScoreDraw() {
        firstPlayerScore.setPoints(40);
        secondPlayerScore.setPoints(30);
        matchScoreCalculationService.calculateMatchScore(2, matchScoreModel);

        assertTrue(matchScoreModel.isDraw());
        assertEquals(0, firstPlayerScore.getPoints());
        assertEquals(0, secondPlayerScore.getPoints());
    }


    @Test
    void testCalculateMatchScoreWinGame() {
        firstPlayerScore.setPoints(40);
        secondPlayerScore.setPoints(0);

        matchScoreCalculationService.calculateMatchScore(1, matchScoreModel);

        assertEquals(1, firstPlayerScore.getGame());
        assertEquals(0, firstPlayerScore.getPoints());
        assertEquals(0, secondPlayerScore.getPoints());
    }

    @Test
    void testCheckMatchWinnerFirstPlayerWins() {
        firstPlayerScore.setSets(2);
        secondPlayerScore.setSets(1);

        assertTrue(matchScoreCalculationService.checkMatchWinner(firstPlayerScore, secondPlayerScore, matchScoreModel));
        verify(match).setWinner(firstPlayer);
    }

    @Test
    void testCheckMatchWinnerNoWinner() {
        firstPlayerScore.setSets(1);
        secondPlayerScore.setSets(1);

        assertFalse(matchScoreCalculationService.checkMatchWinner(firstPlayerScore, secondPlayerScore, matchScoreModel));
        verify(match, never()).setWinner(any(Player.class));
    }

    @Test
    void testHandleRegularPlayDraw() {
        firstPlayerScore.setPoints(30);
        secondPlayerScore.setPoints(40);

        matchScoreCalculationService.handleRegularPlay(1, 1, firstPlayerScore, secondPlayerScore, matchScoreModel);
        assertTrue(matchScoreModel.isDraw());
    }

    @Test
    void testHandleTieBreakPlay() {
        matchScoreModel.setTieBreak(true);
        firstPlayerScore.setPoints(5);
        secondPlayerScore.setPoints(4);

        matchScoreCalculationService.handleTieBreakPlay(1, 1, firstPlayerScore, secondPlayerScore, matchScoreModel);
        assertEquals(6, firstPlayerScore.getPoints());
    }

    @Test
    void testCheckSetWinner() {
        firstPlayerScore.setGame(6);
        secondPlayerScore.setGame(4);

        matchScoreCalculationService.checkSetWinner(firstPlayerScore, secondPlayerScore, matchScoreModel);
        assertEquals(1, firstPlayerScore.getSets());
        assertEquals(0, firstPlayerScore.getGame());
        assertEquals(0, secondPlayerScore.getGame());
    }

    @Test
    void testTieBreakStartsAtSixGamesEach() {
        firstPlayerScore.setGame(6);
        secondPlayerScore.setGame(6);

        matchScoreCalculationService.checkSetWinner(firstPlayerScore, secondPlayerScore, matchScoreModel);
        assertTrue(matchScoreModel.isTieBreak());
    }

}