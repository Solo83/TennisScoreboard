package com.solo83.tennisscoreboard.controller;

import java.io.IOException;
import java.util.Map;

import com.solo83.tennisscoreboard.dto.GetPlayerRequestDTO;
import com.solo83.tennisscoreboard.service.PlayerService;
import com.solo83.tennisscoreboard.service.PlayerServiceImpl;
import com.solo83.tennisscoreboard.utils.exception.RepositoryException;
import com.solo83.tennisscoreboard.utils.exception.ValidatorException;
import com.solo83.tennisscoreboard.utils.validator.PlayerNameValidator;
import jakarta.servlet.ServletException;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.solo83.tennisscoreboard.entity.Player;
import com.solo83.tennisscoreboard.repository.PlayerRepositoryImpl;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@WebServlet(value = "/new-match")
public class NewMatch extends HttpServlet {
    private final PlayerService playerService = PlayerServiceImpl.getInstance();
    private final PlayerNameValidator playerNameValidator = new PlayerNameValidator();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {

        Map<String, String[]> parameterMap = req.getParameterMap();

        try {

            playerNameValidator.validate(parameterMap, "player1name");
            playerNameValidator.validate(parameterMap, "player2name");

            String firstPlayerName = req.getParameter("player1name");
            String secondPlayerName = req.getParameter("player2name");

            GetPlayerRequestDTO player1 = new GetPlayerRequestDTO(firstPlayerName);
            GetPlayerRequestDTO player2 = new GetPlayerRequestDTO(secondPlayerName);

            playerService.create(player1);
            playerService.create(player2);

        } catch (ValidatorException | RepositoryException e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("new_match.jsp").forward(req, resp);
        }

    }
}