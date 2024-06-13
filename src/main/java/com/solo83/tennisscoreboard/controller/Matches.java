package com.solo83.tennisscoreboard.controller;

import com.solo83.tennisscoreboard.dto.MatchSearchRequest;
import com.solo83.tennisscoreboard.dto.Page;
import com.solo83.tennisscoreboard.entity.Match;
import com.solo83.tennisscoreboard.service.FinishedMatchesPersistenceService;
import com.solo83.tennisscoreboard.utils.exception.RepositoryException;
import com.solo83.tennisscoreboard.dto.Pageable;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@WebServlet(value = "/matches")
public class Matches extends HttpServlet {

    private final FinishedMatchesPersistenceService finishedMatchesPersistenceService = FinishedMatchesPersistenceService.getInstance();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String playerName = req.getParameter("filter_by_player_name");
            int pageNumber = getPageNumber(req.getParameter("page"));

            Pageable pageable = createPageable(pageNumber);
            MatchSearchRequest matchSearchRequest = new MatchSearchRequest(playerName);
            Page<Match> matchesPage = finishedMatchesPersistenceService.getFinishedMatchesPage(pageable, matchSearchRequest);

            req.setAttribute("playerName", playerName);
            req.setAttribute("matchesList", matchesPage.getMatches());
            req.setAttribute("noOfPages", matchesPage.getPagesQuantity());
            req.setAttribute("currentPage", pageNumber);

            req.getRequestDispatcher("matches.jsp").forward(req, resp);
        } catch (RepositoryException e) {
            req.setAttribute("error", e);
        }
    }

    private int getPageNumber(String page) {
        return (page != null) ? Integer.parseInt(page) : 1;
    }

    private Pageable createPageable(int pageNumber) {
        Pageable pageable = new Pageable();
        pageable.setPageNumber(pageNumber);
        return pageable;
    }
}
