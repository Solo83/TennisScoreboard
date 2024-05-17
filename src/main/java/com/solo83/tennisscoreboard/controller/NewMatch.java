package com.solo83.tennisscoreboard.controller;

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

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {

        Map<String, String[]> parameterMap = req.getParameterMap();

        for (Map.Entry<String, String[]> stringEntry : parameterMap.entrySet()) {

            System.out.println(stringEntry.getKey() + ": " + Arrays.toString(stringEntry.getValue()));

        }

        PlayerRepositoryImpl playerRepository = new PlayerRepositoryImpl();

        Player player = playerRepository.getPlayer("Novak Djokovic").get();
        List<Player> allPlayers = playerRepository.getAllPlayers();





    }
}