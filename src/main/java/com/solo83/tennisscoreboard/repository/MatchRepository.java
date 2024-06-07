package com.solo83.tennisscoreboard.repository;

import com.solo83.tennisscoreboard.entity.Match;
import com.solo83.tennisscoreboard.utils.exception.RepositoryException;

import java.util.List;
import java.util.Optional;

public interface MatchRepository {

    Optional<Match> getMatchById(Integer id) throws RepositoryException;
    List<Match> getAllMatches() throws RepositoryException;
    Optional<Match> save(Match match) throws RepositoryException;

}
