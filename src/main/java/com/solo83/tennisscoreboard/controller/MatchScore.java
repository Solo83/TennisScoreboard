package com.solo83.tennisscoreboard.controller;

import com.solo83.tennisscoreboard.dto.MatchScoreModel;
import com.solo83.tennisscoreboard.service.MatchScoreCalculationService;
import com.solo83.tennisscoreboard.service.OngoingMatchesService;
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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        UUID uuid = UUID.fromString(req.getParameter("uuid"));
        Integer playerId = Integer.valueOf(req.getParameter("playerId"));
        MatchScoreModel currentMatch = ongoingMatchesService.getMatch(uuid);
        matchScoreCalculationService.calculateMatchScore(playerId, currentMatch);
        resp.sendRedirect("match-score.jsp?uuid=" + uuid);
    }
}
