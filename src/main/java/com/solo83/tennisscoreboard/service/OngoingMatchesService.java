package com.solo83.tennisscoreboard.service;

import com.solo83.tennisscoreboard.dto.GetPlayerRequest;
import com.solo83.tennisscoreboard.dto.OngoingMatch;
import com.solo83.tennisscoreboard.dto.PlayerScore;
import com.solo83.tennisscoreboard.entity.Match;
import com.solo83.tennisscoreboard.entity.Player;
import com.solo83.tennisscoreboard.repository.OngoingMatchesRepository;
import com.solo83.tennisscoreboard.repository.OngoingMatchesRepositoryImpl;
import com.solo83.tennisscoreboard.utils.exception.RepositoryException;

import java.util.UUID;


public class OngoingMatchesService {
    private final Mapper mapper = Mapper.getInstance();
    private final PlayerService playerService = PlayerServiceImpl.getInstance();
    private final FinishedMatchesPersistenceService persistenceService = FinishedMatchesPersistenceService.getInstance();
    private final OngoingMatchesRepository ongoingMatchesRepository = OngoingMatchesRepositoryImpl.getInstance();

    private static OngoingMatchesService instance;


    private OngoingMatchesService() {
    }

    public static OngoingMatchesService getInstance() {
        if (instance == null) {
            instance = new OngoingMatchesService();
        }
        return instance;
    }

    public UUID createNewMatch(String firstPlayerName, String secondPlayerName) throws RepositoryException {

        GetPlayerRequest player1 = new GetPlayerRequest(firstPlayerName);
        GetPlayerRequest player2 = new GetPlayerRequest(secondPlayerName);

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

    public void persistMatch(OngoingMatch ongoingMatch) throws RepositoryException {
        Match match = mapper.toMatch(ongoingMatch);
        persistenceService.persistMatch(match);
    }

    public void removeMatch(UUID uuid) {
        ongoingMatchesRepository.remove(uuid);
    }


}
