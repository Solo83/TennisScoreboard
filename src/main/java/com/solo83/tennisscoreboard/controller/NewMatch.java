package com.solo83.tennisscoreboard.controller;

import java.io.IOException;
import java.util.Map;

import com.solo83.tennisscoreboard.entity.Player;
import com.solo83.tennisscoreboard.service.PlayerService;
import com.solo83.tennisscoreboard.utils.exception.RepositoryException;
import com.solo83.tennisscoreboard.utils.exception.ValidatorException;
import com.solo83.tennisscoreboard.utils.validator.Expressions;
import com.solo83.tennisscoreboard.utils.validator.ParameterValidator;
import jakarta.servlet.ServletException;


import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(value = "/new-match")
public class NewMatch extends HttpServlet {
    PlayerService playerService = PlayerService.getInstance();
    ParameterValidator parameterValidator = new ParameterValidator();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Map<String, String[]> parameterMap = req.getParameterMap();

        try {

            parameterValidator.validate(parameterMap, "player1name", Expressions.PLAYER_PATTERN);
            parameterValidator.validate(parameterMap, "player2name", Expressions.PLAYER_PATTERN);

            String firstPlayerName = req.getParameter("player1name");
            String secondPlayerName = req.getParameter("player2name");

            playerService.savePlayer(new Player(firstPlayerName));
            playerService.savePlayer(new Player(secondPlayerName));

        } catch (ValidatorException | RepositoryException e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("new_match.jsp").forward(req, resp);
        }

    }
}