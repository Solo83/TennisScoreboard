package com.solo83.tennisscoreboard.controller;

import com.solo83.tennisscoreboard.dto.OngoingMatch;
import com.solo83.tennisscoreboard.service.MatchScoreCalculationService;
import com.solo83.tennisscoreboard.service.OngoingMatchesService;
import com.solo83.tennisscoreboard.utils.exception.RepositoryException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.UUID;


@Slf4j
@WebServlet(value = "/match-score")
public class MatchScore extends HttpServlet {
    private final MatchScoreCalculationService matchScoreCalculationService = MatchScoreCalculationService.getInstance();
    private final OngoingMatchesService ongoingMatchesService = OngoingMatchesService.getInstance();


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        UUID uuid = UUID.fromString(req.getParameter("uuid"));
        Integer playerId = Integer.valueOf(req.getParameter("playerId"));
        OngoingMatch currentOngoingMatch = ongoingMatchesService.getMatch(uuid);
        boolean isFinished = matchScoreCalculationService.calculateMatchScore(playerId, currentOngoingMatch);

        if (!isFinished) {
            resp.sendRedirect("match-score.jsp?uuid=" + uuid);
        } else {
            try {
                req.setAttribute("currentMatch", currentOngoingMatch);
                req.getRequestDispatcher("finished-match.jsp").forward(req, resp);
                ongoingMatchesService.removeMatch(uuid);
                log.info("Match removed");
                ongoingMatchesService.persistMatch(currentOngoingMatch);
                log.info("Match added to repository");
            } catch (RepositoryException e) {
                log.error(e.getMessage());
                req.setAttribute("error", e.getMessage());
            }

        }
    }
}
