package com.solo83.tennisscoreboard.controller;

import com.solo83.tennisscoreboard.entity.Match;
import com.solo83.tennisscoreboard.service.MatchesPaginationService;
import com.solo83.tennisscoreboard.utils.exception.RepositoryException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;

@Slf4j
@WebServlet(value = "/matches")
public class Matches extends HttpServlet {

    private final MatchesPaginationService matchesPaginationService = MatchesPaginationService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int pageNumber = 1;
        int noOfRecords = 0;
        int noOfPages;
        List<Match> matchesPage = List.of();

        String playerName = req.getParameter("filter_by_player_name");
        String page = req.getParameter("page");

        if (playerName != null && !playerName.isEmpty()) {
            try {
                if (page != null) {
                    pageNumber = Integer.parseInt(req.getParameter("page"));
                }
                req.setAttribute("playerName", playerName);
                matchesPage = matchesPaginationService.getMatchesPageByPlayerName(playerName, pageNumber);
                noOfRecords = matchesPaginationService.getMatchesCountByPlayerName(playerName);
            } catch (RepositoryException e) {
                req.setAttribute("error", e);
            }
        } else {
            if (page != null) {
                pageNumber = Integer.parseInt(req.getParameter("page"));
            }
            try {
                matchesPage = matchesPaginationService.getMatchesPage(pageNumber);
                noOfRecords = matchesPaginationService.getAllMatchesCount();
            } catch (RepositoryException e) {
                req.setAttribute("error", e);
            }
        }

        noOfPages = matchesPaginationService.getNoOfPages(noOfRecords);
        req.setAttribute("matchesList", matchesPage);
        req.setAttribute("noOfPages", noOfPages);
        req.setAttribute("currentPage", pageNumber);
        RequestDispatcher view = req.getRequestDispatcher("matches.jsp");
        view.forward(req, resp);
    }
}
