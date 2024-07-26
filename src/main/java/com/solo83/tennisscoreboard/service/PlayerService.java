package com.solo83.tennisscoreboard.service;

import com.solo83.tennisscoreboard.dto.PlayerFromRequest;
import com.solo83.tennisscoreboard.entity.Player;
import com.solo83.tennisscoreboard.utils.exception.RepositoryException;
import com.solo83.tennisscoreboard.utils.exception.ValidatorException;


public interface PlayerService {
    Player createOrGet(PlayerFromRequest playerFromRequest) throws RepositoryException;
    void checkPlayersEquality(PlayerFromRequest player1, PlayerFromRequest player2) throws ValidatorException;
}
