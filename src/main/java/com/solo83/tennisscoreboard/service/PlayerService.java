package com.solo83.tennisscoreboard.service;

import com.solo83.tennisscoreboard.dto.PlayerFromRequest;
import com.solo83.tennisscoreboard.entity.Player;

public interface PlayerService {
    Player createOrGet(PlayerFromRequest playerFromRequest);
    void checkPlayersEquality(PlayerFromRequest player1, PlayerFromRequest player2);
}
