package com.solo83.tennisscoreboard.service;

import com.solo83.tennisscoreboard.entity.Match;
import com.solo83.tennisscoreboard.repository.MatchRepository;
import com.solo83.tennisscoreboard.repository.MatchRepositoryImpl;
import com.solo83.tennisscoreboard.utils.exception.RepositoryException;

public class FinishedMatchesPersistenceService {
    private final MatchRepository matchRepository = MatchRepositoryImpl.getInstance();
    private static FinishedMatchesPersistenceService instance;

    private FinishedMatchesPersistenceService() {
    }

    public static FinishedMatchesPersistenceService getInstance() {
        if (instance == null) {
            instance = new FinishedMatchesPersistenceService();
        }
        return instance;
    }

    public void persistMatch(Match match) throws RepositoryException {
        matchRepository.save(match);
    }

}
