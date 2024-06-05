package com.solo83.tennisscoreboard.service;
import com.solo83.tennisscoreboard.dto.GetPlayerRequest;
import com.solo83.tennisscoreboard.dto.MatchScoreModel;
import com.solo83.tennisscoreboard.dto.PlayerScore;
import com.solo83.tennisscoreboard.entity.Match;
import com.solo83.tennisscoreboard.entity.Player;
import com.solo83.tennisscoreboard.utils.exception.RepositoryException;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class OngoingMatchesService {
    private final PlayerService playerService = PlayerServiceImpl.getInstance();
    private final FinishedMatchesPersistenceService persistenceService = FinishedMatchesPersistenceService.getInstance();

    private static OngoingMatchesService instance;

    private final Map<UUID, MatchScoreModel> matches = new ConcurrentHashMap<>();

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

        Optional<Player> firstPlayer = playerService.createOrGet(player1);
        Optional<Player> secondPlayer = playerService.createOrGet(player2);

        UUID uuid = UUID.randomUUID();
        MatchScoreModel model = new MatchScoreModel();
        Match match = new Match();
        match.setFirstPlayer(firstPlayer.orElse(null));
        match.setSecondPlayer(secondPlayer.orElse(null));
        model.setMatch(match);

        PlayerScore player1Score = new PlayerScore();
        PlayerScore player2Score = new PlayerScore();

        model.setFirstPlayerScore(player1Score);
        model.setSecondPlayerScore(player2Score);

        matches.put(uuid, model);
        return uuid;
    }

    public MatchScoreModel getMatch(UUID uuid) {
        return matches.get(uuid);
    }

    public void persistMatch(MatchScoreModel matchScoreModel) throws RepositoryException {
        Match match = matchScoreModel.getMatch();
        persistenceService.persistMatch(match);
    }

    public void removeMatch(UUID uuid)  {
        matches.remove(uuid);
    }

}
