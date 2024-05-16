package com.solo83.tennisscoreboard.service;

import com.solo83.tennisscoreboard.dto.GetPlayerRequestDTO;
import com.solo83.tennisscoreboard.entity.Player;

public class Mapper {
    public GetPlayerRequestDTO toDto(Player player) {
        String name = player.getName();

        return new GetPlayerRequestDTO(name);
    }

    public Player toPlayer(GetPlayerRequestDTO getPlayerRequestDTO) {
        return new Player(getPlayerRequestDTO.getName());
    }
}
