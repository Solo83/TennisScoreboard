package com.solo83.tennisscoreboard.service;

import com.solo83.tennisscoreboard.dto.MatchSearchRequest;
import com.solo83.tennisscoreboard.dto.Page;
import com.solo83.tennisscoreboard.entity.Match;
import com.solo83.tennisscoreboard.repository.MatchRepository;
import com.solo83.tennisscoreboard.dto.Pageable;

import java.util.List;

public class FinishedMatchesPersistenceService {
    private final MatchRepository matchRepository;
    private static FinishedMatchesPersistenceService instance;

    private FinishedMatchesPersistenceService(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    public static FinishedMatchesPersistenceService getInstance(MatchRepository matchRepository) {
        if (instance == null) {
            instance = new FinishedMatchesPersistenceService(matchRepository);
        }
        return instance;
    }

    public void persistMatch(Match match) {
        matchRepository.save(match);
    }

    public Page<Match> getFinishedMatchesPage(Pageable pageable, MatchSearchRequest matchSearchRequest) {
        List<Match> allMatches;
        if (matchSearchRequest.name() == null || matchSearchRequest.name().isEmpty()) {
            allMatches = matchRepository.getAll();
        } else {
            allMatches = matchRepository.getAllMatchesByPlayerName(matchSearchRequest.name());
        }
        return createPageFromMatches(pageable, allMatches);
    }

    private Page<Match> createPageFromMatches(Pageable pageable, List<Match> matches) {
        Page<Match> foundedMatchesPage = new Page<>();
        foundedMatchesPage.setPagesQuantity(pageable.getPagesQuantity(matches.size()));
        foundedMatchesPage.setMatches(matches.stream()
                .skip(pageable.getSkipCount())
                .limit(pageable.getRESULTS_PER_PAGE())
                .toList());
        return foundedMatchesPage;
    }
}
