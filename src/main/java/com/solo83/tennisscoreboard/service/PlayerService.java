package com.solo83.tennisscoreboard.service;

import com.solo83.tennisscoreboard.entity.Player;
import com.solo83.tennisscoreboard.repository.PlayerRepositoryImpl;
import com.solo83.tennisscoreboard.utils.exception.RepositoryException;

public class PlayerService {
    PlayerRepositoryImpl playerRepository = PlayerRepositoryImpl.getInstance();
    private static PlayerService instance;

    private PlayerService() {
    }

    public static PlayerService getInstance() {
        if (instance == null) {
            instance = new PlayerService();
        }
        return instance;
    }

    public void savePlayer(Player player) throws RepositoryException {
        playerRepository.addPlayer(player);
    }

}
