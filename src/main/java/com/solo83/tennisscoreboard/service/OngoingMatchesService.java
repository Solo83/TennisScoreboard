package com.solo83.tennisscoreboard.service;

import com.solo83.tennisscoreboard.dto.PlayerFromRequest;
import com.solo83.tennisscoreboard.dto.OngoingMatch;
import com.solo83.tennisscoreboard.dto.PlayerScore;
import com.solo83.tennisscoreboard.entity.Match;
import com.solo83.tennisscoreboard.entity.Player;
import com.solo83.tennisscoreboard.repository.OngoingMatchesRepository;

import java.util.UUID;

public class OngoingMatchesService {
    private final Mapper mapper;
    private final PlayerService playerService;
    private final FinishedMatchesPersistenceService persistenceService;
    private final OngoingMatchesRepository ongoingMatchesRepository;
    private static OngoingMatchesService instance;

    private OngoingMatchesService(OngoingMatchesRepository ongoingMatchesRepository, PlayerService playerService, FinishedMatchesPersistenceService persistenceService, Mapper mapper) {
        this.ongoingMatchesRepository = ongoingMatchesRepository;
        this.playerService = playerService;
        this.persistenceService = persistenceService;
        this.mapper = mapper;
    }

    public static OngoingMatchesService getInstance(OngoingMatchesRepository ongoingMatchesRepository, PlayerService playerService, FinishedMatchesPersistenceService persistenceService, Mapper mapper) {
        if (instance == null) {
            instance = new OngoingMatchesService(ongoingMatchesRepository, playerService, persistenceService, mapper);
        }
        return instance;
    }

    public UUID createNewMatch(String firstPlayerName, String secondPlayerName) {
        PlayerFromRequest player1 = new PlayerFromRequest(firstPlayerName);
        PlayerFromRequest player2 = new PlayerFromRequest(secondPlayerName);
        playerService.checkPlayersEquality(player1, player2);
        Player firstPlayer = playerService.createOrGet(player1);
        Player secondPlayer = playerService.createOrGet(player2);
        UUID uuid = UUID.randomUUID();
        OngoingMatch model = new OngoingMatch();
        model.setFirstPlayer(firstPlayer);
        model.setSecondPlayer(secondPlayer);
        PlayerScore player1Score = new PlayerScore();
        PlayerScore player2Score = new PlayerScore();
        model.setFirstPlayerScore(player1Score);
        model.setSecondPlayerScore(player2Score);
        ongoingMatchesRepository.save(uuid, model);
        return uuid;
    }

    public OngoingMatch getMatch(UUID uuid) {
        return ongoingMatchesRepository.get(uuid);
    }

    public void persistMatch(OngoingMatch ongoingMatch) {
        Match match = mapper.toMatch(ongoingMatch);
        persistenceService.persistMatch(match);
    }

    public void removeMatch(UUID uuid) {
        ongoingMatchesRepository.remove(uuid);
    }
}
