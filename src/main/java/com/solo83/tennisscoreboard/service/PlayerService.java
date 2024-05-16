package com.solo83.tennisscoreboard.service;

import com.solo83.tennisscoreboard.dto.GetPlayerRequestDTO;
import com.solo83.tennisscoreboard.entity.Player;
import com.solo83.tennisscoreboard.utils.exception.RepositoryException;

import java.util.Optional;

public interface PlayerService {
    Optional<Player> create(GetPlayerRequestDTO getPlayerRequestDTO) throws RepositoryException;
}
