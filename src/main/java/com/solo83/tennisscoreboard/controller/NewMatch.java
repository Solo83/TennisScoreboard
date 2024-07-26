package com.solo83.tennisscoreboard.controller;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import com.solo83.tennisscoreboard.service.OngoingMatchesService;
import com.solo83.tennisscoreboard.utils.exception.RepositoryException;
import com.solo83.tennisscoreboard.utils.exception.ValidatorException;
import com.solo83.tennisscoreboard.utils.validator.PlayerNameValidator;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebServlet(value = "/new-match")
public class NewMatch extends HttpServlet {
    private PlayerNameValidator playerNameValidator;
    private OngoingMatchesService ongoingMatchesService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        playerNameValidator = (PlayerNameValidator) config.getServletContext().getAttribute("playerNameValidator");
        ongoingMatchesService = (OngoingMatchesService) config.getServletContext().getAttribute("ongoingMatchesService");
    }

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