package com.solo83.tennisscoreboard.listener;

import com.solo83.tennisscoreboard.repository.MatchRepository;
import com.solo83.tennisscoreboard.repository.OngoingMatchesRepository;
import com.solo83.tennisscoreboard.repository.OngoingMatchesRepositoryImpl;
import com.solo83.tennisscoreboard.repository.PlayerRepository;
import com.solo83.tennisscoreboard.service.FinishedMatchesPersistenceService;
import com.solo83.tennisscoreboard.service.Mapper;
import com.solo83.tennisscoreboard.service.MatchScoreCalculationService;
import com.solo83.tennisscoreboard.service.OngoingMatchesService;
import com.solo83.tennisscoreboard.service.PlayerService;
import com.solo83.tennisscoreboard.service.PlayerServiceImpl;
import com.solo83.tennisscoreboard.utils.RepositoryFactory;
import com.solo83.tennisscoreboard.utils.validator.PlayerNameValidator;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

public class AppContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContextListener.super.contextInitialized(sce);
        final PlayerRepository playerRepository = RepositoryFactory.getPlayerRepository();
        final MatchRepository matchRepository = RepositoryFactory.getMatchRepository();
        final FinishedMatchesPersistenceService finishedMatchesPersistenceService = FinishedMatchesPersistenceService.getInstance(matchRepository);
        final MatchScoreCalculationService matchScoreCalculationService = MatchScoreCalculationService.getInstance();
        final Mapper mapper = Mapper.getInstance();
        final PlayerService playerService = PlayerServiceImpl.getInstance(playerRepository, mapper);
        final OngoingMatchesRepository ongoingMatchesRepository = OngoingMatchesRepositoryImpl.getInstance();
        final OngoingMatchesService ongoingMatchesService = OngoingMatchesService.getInstance(ongoingMatchesRepository, playerService, finishedMatchesPersistenceService, mapper);
        final PlayerNameValidator playerNameValidator = new PlayerNameValidator();

        sce.getServletContext().setAttribute("finishedMatchesPersistenceService", finishedMatchesPersistenceService);
        sce.getServletContext().setAttribute("matchScoreCalculationService", matchScoreCalculationService);
        sce.getServletContext().setAttribute("ongoingMatchesService", ongoingMatchesService);
        sce.getServletContext().setAttribute("playerNameValidator", playerNameValidator);
    }
}
