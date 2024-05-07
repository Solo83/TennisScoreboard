package com.solo83.tennisscoreboard.repository;

import com.solo83.tennisscoreboard.entity.Player;
import com.solo83.tennisscoreboard.utils.exception.RepositoryException;

import java.util.List;
import java.util.Optional;

public interface PlayerRepository {
    Optional<Player> getPlayer(String playerName) throws RepositoryException;
    List<Player> getAllPlayers() throws RepositoryException;
    Optional<Player> addPlayer(Player player) throws RepositoryException;
}
