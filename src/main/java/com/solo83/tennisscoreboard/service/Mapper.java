package com.solo83.tennisscoreboard.service;

import com.solo83.tennisscoreboard.dto.GetPlayerRequest;
import com.solo83.tennisscoreboard.dto.OngoingMatch;
import com.solo83.tennisscoreboard.entity.Match;
import com.solo83.tennisscoreboard.entity.Player;

public class Mapper {

    private static Mapper instance;

    private Mapper() {
    }

    public static Mapper getInstance() {
        if (instance == null) {
            instance = new Mapper();
        }
        return instance;
    }


    public GetPlayerRequest toDto(Player player) {
        String name = player.getName();
        return new GetPlayerRequest(name);
    }

    public Player toPlayer(GetPlayerRequest getPlayerRequest) {
        return new Player(getPlayerRequest.name());
    }

    public Match toMatch(OngoingMatch ongoingMatch) {
        Match match = new Match();
        match.setFirstPlayer(ongoingMatch.getFirstPlayer());
        match.setSecondPlayer(ongoingMatch.getSecondPlayer());
        match.setWinner(ongoingMatch.getWinner());
        return match;
    }
}
