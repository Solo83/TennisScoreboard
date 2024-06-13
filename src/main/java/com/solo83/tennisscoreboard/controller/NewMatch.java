package com.solo83.tennisscoreboard.controller;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import com.solo83.tennisscoreboard.service.OngoingMatchesService;
import com.solo83.tennisscoreboard.utils.exception.RepositoryException;
import com.solo83.tennisscoreboard.utils.exception.ValidatorException;
import com.solo83.tennisscoreboard.utils.validator.PlayerNameValidator;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@WebServlet(value = "/new-match")
public class NewMatch extends HttpServlet {

    private final PlayerNameValidator playerNameValidator = new PlayerNameValidator();
    private final OngoingMatchesService ongoingMatchesService = OngoingMatchesService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Map<String, String[]> parameterMap = req.getParameterMap();

        try {

            playerNameValidator.validate(parameterMap, "player1name");
            playerNameValidator.validate(parameterMap, "player2name");
            log.info("Players names corrected and validated");

            String firstPlayerName = req.getParameter("player1name");
            String secondPlayerName = req.getParameter("player2name");

            UUID uuid = ongoingMatchesService.createNewMatch(firstPlayerName, secondPlayerName);
            log.info("New match created, uuid: {}", uuid);
            resp.sendRedirect("match-score.jsp?uuid=" + uuid);

        } catch (ValidatorException | RepositoryException e) {
            log.error(e.getMessage());
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("new-match.jsp").forward(req, resp);
        }
    }
}