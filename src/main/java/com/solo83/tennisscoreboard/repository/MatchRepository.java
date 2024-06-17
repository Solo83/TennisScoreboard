package com.solo83.tennisscoreboard.repository;

import com.solo83.tennisscoreboard.entity.Match;
import com.solo83.tennisscoreboard.utils.exception.RepositoryException;

import java.util.List;
import java.util.Optional;

public interface MatchRepository {
    List<Match> getAll() throws RepositoryException;
    List<Match> getAllMatchesByPlayerName(String playerName) throws RepositoryException;
    Optional<Match> save(Match match) throws RepositoryException;
}
