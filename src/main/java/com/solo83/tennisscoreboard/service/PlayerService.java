package com.solo83.tennisscoreboard.service;

import com.solo83.tennisscoreboard.dto.PlayerDTO;
import com.solo83.tennisscoreboard.utils.exception.RepositoryException;

public interface PlayerService {
    void create(PlayerDTO playerDTO) throws RepositoryException;
}
