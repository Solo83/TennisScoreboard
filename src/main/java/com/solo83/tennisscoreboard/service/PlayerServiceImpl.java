package com.solo83.tennisscoreboard.service;

import com.solo83.tennisscoreboard.dto.GetPlayerRequest;
import com.solo83.tennisscoreboard.entity.Player;
import com.solo83.tennisscoreboard.repository.PlayerRepository;
import com.solo83.tennisscoreboard.repository.PlayerRepositoryImpl;
import com.solo83.tennisscoreboard.utils.exception.RepositoryException;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
public class PlayerServiceImpl implements PlayerService {
    private final PlayerRepository playerRepository = PlayerRepositoryImpl.getInstance();
    private static PlayerServiceImpl instance;
    private final Mapper mapper = Mapper.getInstance();

    private PlayerServiceImpl() {
    }

    public static PlayerServiceImpl getInstance() {
        if (instance == null) {
            instance = new PlayerServiceImpl();
        }
        return instance;
    }


    @Override
    public Player createOrGet(GetPlayerRequest getPlayerRequest) throws RepositoryException {
        Player player = mapper.toPlayer(getPlayerRequest);
        Optional<Player> currentPlayer;
        try {
            currentPlayer = playerRepository.getPlayerByName(player.getName());
        } catch (RepositoryException e) {
            currentPlayer = playerRepository.save(player);
        }
        log.info("Existing player: {}", currentPlayer.orElse(null));

        return currentPlayer.orElse(null);
    }
}
