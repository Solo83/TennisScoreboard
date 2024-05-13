package com.solo83.tennisscoreboard.service;

import com.solo83.tennisscoreboard.dto.PlayerDTO;
import com.solo83.tennisscoreboard.entity.Player;
import com.solo83.tennisscoreboard.repository.PlayerRepository;
import com.solo83.tennisscoreboard.repository.PlayerRepositoryImpl;
import com.solo83.tennisscoreboard.utils.exception.RepositoryException;

public class PlayerServiceImpl implements PlayerService {
    private final PlayerRepository playerRepository = PlayerRepositoryImpl.getInstance();
    private static PlayerServiceImpl instance;
    private final Mapper mapper = new Mapper();

    private PlayerServiceImpl() {
    }

    public static PlayerServiceImpl getInstance() {
        if (instance == null) {
            instance = new PlayerServiceImpl();
        }
        return instance;
    }

    public void create(PlayerDTO playerDTO) throws RepositoryException {
        Player player = mapper.toPlayer(playerDTO);
        playerRepository.addPlayer(player);
    }

}
