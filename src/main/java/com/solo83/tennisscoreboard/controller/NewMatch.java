package com.solo83.tennisscoreboard.controller;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.solo83.tennisscoreboard.entity.Player;
import com.solo83.tennisscoreboard.repository.PlayerRepositoryImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(value = "/new-match")
public class NewMatch extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Map<String, String[]> parameterMap = req.getParameterMap();

        for (Map.Entry<String, String[]> stringEntry : parameterMap.entrySet()) {

            System.out.println(stringEntry.getKey() + ": " + Arrays.toString(stringEntry.getValue()));

        }

        PlayerRepositoryImpl playerRepository = new PlayerRepositoryImpl();

        Player player = playerRepository.getPlayer("Novak Djokovic").get();
        List<Player> allPlayers = playerRepository.getAllPlayers();



    }
}