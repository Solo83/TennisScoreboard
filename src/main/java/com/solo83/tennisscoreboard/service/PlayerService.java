package com.solo83.tennisscoreboard.service;

import com.solo83.tennisscoreboard.dto.GetPlayerRequest;
import com.solo83.tennisscoreboard.entity.Player;
import com.solo83.tennisscoreboard.utils.exception.RepositoryException;
import com.solo83.tennisscoreboard.utils.exception.ValidatorException;


public interface PlayerService {
    Player createOrGet(GetPlayerRequest getPlayerRequest) throws RepositoryException;
    void checkPlayersEquality(GetPlayerRequest player1, GetPlayerRequest player2) throws ValidatorException;
}
