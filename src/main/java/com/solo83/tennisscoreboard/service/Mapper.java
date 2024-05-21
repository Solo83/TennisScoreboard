package com.solo83.tennisscoreboard.service;

import com.solo83.tennisscoreboard.dto.GetPlayerRequest;
import com.solo83.tennisscoreboard.entity.Player;

public class Mapper {
    public GetPlayerRequest toDto(Player player) {
        String name = player.getName();

        return new GetPlayerRequest(name);
    }

    public Player toPlayer(GetPlayerRequest getPlayerRequest) {
        return new Player(getPlayerRequest.name());
    }
}
