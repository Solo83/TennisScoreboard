package com.solo83.tennisscoreboard.controller;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import com.solo83.tennisscoreboard.dto.GetPlayerRequest;
import com.solo83.tennisscoreboard.entity.Player;
import com.solo83.tennisscoreboard.service.OngoingMatchesService;
import com.solo83.tennisscoreboard.service.PlayerService;
import com.solo83.tennisscoreboard.service.PlayerServiceImpl;
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
    private final PlayerService playerService = PlayerServiceImpl.getInstance();
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

            GetPlayerRequest player1 = new GetPlayerRequest(firstPlayerName);
            GetPlayerRequest player2 = new GetPlayerRequest(secondPlayerName);

            Optional<Player> currentPlayer1;
            Optional<Player> currentPlayer2;

            currentPlayer1 = playerService.get(player1);

            if (currentPlayer1.isEmpty()) {
                currentPlayer1 = playerService.create(player1);
                log.info("Player1 created");
            }

            currentPlayer2 = playerService.get(player2);

            if (currentPlayer2.isEmpty()) {
                currentPlayer2 = playerService.create(player2);
                log.info("Player2 created");
            }

            if (currentPlayer1.isPresent() && currentPlayer2.isPresent()) {
                UUID uuid = ongoingMatchesService.createNewMatch(currentPlayer1.get(), currentPlayer2.get());
                log.info("New match created");
                resp.sendRedirect("match-score.jsp?uuid=" + uuid);
            }


        } catch (ValidatorException | RepositoryException e) {
            log.error(e.getMessage());
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("new-match.jsp").forward(req, resp);
        }


    }
}