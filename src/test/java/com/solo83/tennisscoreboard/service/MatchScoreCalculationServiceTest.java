package com.solo83.tennisscoreboard.service;

import com.solo83.tennisscoreboard.dto.OngoingMatch;
import com.solo83.tennisscoreboard.dto.PlayerScore;
import com.solo83.tennisscoreboard.entity.Match;
import com.solo83.tennisscoreboard.entity.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MatchScoreCalculationServiceTest {

    private MatchScoreCalculationService matchScoreCalculationService;
    private OngoingMatch ongoingMatch;
    private PlayerScore firstPlayerScore;
    private PlayerScore secondPlayerScore;
    private Match match;
    private Player firstPlayer;
    private Player secondPlayer;

    @BeforeEach
    void setUp(){
        matchScoreCalculationService = MatchScoreCalculationService.getInstance();

        firstPlayerScore = new PlayerScore();
        secondPlayerScore = new PlayerScore();
        firstPlayer = mock(Player.class);
        secondPlayer = mock(Player.class);
        match = mock(Match.class);

        when(firstPlayer.getId()).thenReturn(1);
        when(secondPlayer.getId()).thenReturn(2);
        when(match.getFirstPlayer()).thenReturn(firstPlayer);
        when(match.getSecondPlayer()).thenReturn(secondPlayer);

        ongoingMatch = new OngoingMatch();
        ongoingMatch.setFirstPlayerScore(firstPlayerScore);
        ongoingMatch.setSecondPlayerScore(secondPlayerScore);
        ongoingMatch.setMatch(match);
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
        secondPlayerScore.setPoints(30);
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
        verify(match).setWinner(firstPlayer);
    }

    @Test
    void testCheckMatchWinnerNoWinner() {
        firstPlayerScore.setSets(1);
        secondPlayerScore.setSets(1);

        assertFalse(matchScoreCalculationService.checkMatchWinner(firstPlayerScore, secondPlayerScore, ongoingMatch));
        verify(match, never()).setWinner(any(Player.class));
    }

    @Test
    void testHandleRegularPlayDraw() {
        firstPlayerScore.setPoints(30);
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