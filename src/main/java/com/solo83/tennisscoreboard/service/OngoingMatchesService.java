package com.solo83.tennisscoreboard.service;
import com.solo83.tennisscoreboard.dto.MatchScoreModel;
import com.solo83.tennisscoreboard.dto.PlayerScore;
import com.solo83.tennisscoreboard.entity.Match;
import com.solo83.tennisscoreboard.entity.Player;


import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class OngoingMatchesService {
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

    public UUID createNewMatch(Player player1, Player player2) {
        UUID uuid = UUID.randomUUID();
        MatchScoreModel model = new MatchScoreModel();
        Match match = new Match();
        match.setFirstPlayer(player1);
        match.setSecondPlayer(player2);
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

}
