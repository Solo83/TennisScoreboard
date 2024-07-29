package com.solo83.tennisscoreboard.repository;
import com.solo83.tennisscoreboard.entity.Player;

import java.util.List;
import java.util.Optional;

public interface PlayerRepository {
    Optional<Player> getByName(String name) ;
    List<Player> getAll();
    Optional<Player> save(Player entity);
}
