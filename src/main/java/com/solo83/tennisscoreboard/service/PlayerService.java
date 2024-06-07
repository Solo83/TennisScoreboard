package com.solo83.tennisscoreboard.service;

import com.solo83.tennisscoreboard.dto.GetPlayerRequest;
import com.solo83.tennisscoreboard.entity.Player;
import com.solo83.tennisscoreboard.utils.exception.RepositoryException;


public interface PlayerService {
    Player createOrGet(GetPlayerRequest getPlayerRequest) throws RepositoryException;
}
