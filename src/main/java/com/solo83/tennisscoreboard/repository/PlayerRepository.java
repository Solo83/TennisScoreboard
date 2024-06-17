package com.solo83.tennisscoreboard.repository;
import com.solo83.tennisscoreboard.entity.Player;
import com.solo83.tennisscoreboard.utils.exception.RepositoryException;

import java.util.List;
import java.util.Optional;

public interface PlayerRepository {
    Optional<Player> getByName(String name) throws RepositoryException;
    List<Player> getAll() throws RepositoryException;
    Optional<Player> save(Player entity) throws RepositoryException;
}
