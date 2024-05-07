package com.solo83.tennisscoreboard.controller;

import java.io.*;
import java.util.Map;
import java.util.Optional;


import com.solo83.tennisscoreboard.entity.Player;
import com.solo83.tennisscoreboard.repository.PlayerRepositoryImpl;
import com.solo83.tennisscoreboard.utils.exception.RepositoryException;
import com.solo83.tennisscoreboard.utils.exception.ValidatorException;
import com.solo83.tennisscoreboard.utils.validator.Expressions;
import com.solo83.tennisscoreboard.utils.validator.ParameterValidator;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(value = "/new-match")
public class NewMatch extends HttpServlet {
    PlayerRepositoryImpl playerRepository = new PlayerRepositoryImpl();
    ParameterValidator parameterValidator = new ParameterValidator();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Map<String, String[]> parameterMap = req.getParameterMap();

        try {

            parameterValidator.validate(parameterMap, "player1name", Expressions.PLAYER_PATTERN);
            parameterValidator.validate(parameterMap, "player2name", Expressions.PLAYER_PATTERN);

            String firstPlayerName = req.getParameter("player1name");
            String secondPlayerName = req.getParameter("player2name");

            Player player1 = new Player(firstPlayerName);
            Player player2 = new Player(secondPlayerName);

            Optional<Player> player1Optional = playerRepository.addPlayer(player1);
            Optional<Player> player2Optional = playerRepository.addPlayer(player2);

        } catch (ValidatorException | RepositoryException e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("new_match.jsp").forward(req, resp);
        }

    }
}