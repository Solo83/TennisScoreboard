package com.solo83.tennisscoreboard.service;

import com.solo83.tennisscoreboard.entity.Match;
import com.solo83.tennisscoreboard.repository.MatchRepositoryImpl;
import com.solo83.tennisscoreboard.utils.exception.RepositoryException;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class MatchesPaginationService {

    private final int RESULTS_PER_PAGE = 3;
    private static MatchesPaginationService instance;
    private final MatchRepositoryImpl matchRepository = MatchRepositoryImpl.getInstance();


    public static MatchesPaginationService getInstance() {
        if (instance == null) {
            instance = new MatchesPaginationService();
        }
        return instance;
    }

    public List<Match> getMatchesPage(int pageNumber) throws RepositoryException {
        int skipCount = (pageNumber - 1) * RESULTS_PER_PAGE;
        List<Match> allMatches = matchRepository.getAllMatches();
        List<Match> page = allMatches.stream()
                .skip(skipCount)
                .limit(RESULTS_PER_PAGE)
                .toList();
         log.info("Matches per page is {}",page);
        return page;
    }

    public List<Match> getMatchesPageByPlayerName(String playerName, int pageNumber) throws RepositoryException {
        int skipCount = (pageNumber - 1) * RESULTS_PER_PAGE;
        List<Match> list = matchRepository.getMatchesByPlayerName(playerName);
        List<Match> page = list.stream()
                .skip(skipCount)
                .limit(RESULTS_PER_PAGE)
                .toList();
        log.info("Matches by PlayerName per page is {}",list);
        return page;
    }

    public int getAllMatchesCount() throws RepositoryException {
        return matchRepository.getAllMatches().size();
    }

    public int getMatchesCountByPlayerName(String playerName) throws RepositoryException {
        return matchRepository.getMatchesByPlayerName(playerName).size();
    }

    public int getNoOfPages(int noOfRecords){
        return (int) Math.ceil(noOfRecords * 1.0 / RESULTS_PER_PAGE);
    }
}
