package com.solo83.tennisscoreboard.service;

import com.solo83.tennisscoreboard.dto.PlayerDTO;
import com.solo83.tennisscoreboard.entity.Player;

public class Mapper {
    public PlayerDTO toDto(Player player) {
        String name = player.getName();

        return new PlayerDTO(name);
    }

    public Player toPlayer(PlayerDTO playerDTO) {
        return new Player(playerDTO.getName());
    }
}
