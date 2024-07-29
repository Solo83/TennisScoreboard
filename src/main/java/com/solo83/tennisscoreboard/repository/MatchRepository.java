package com.solo83.tennisscoreboard.repository;

import com.solo83.tennisscoreboard.entity.Match;

import java.util.List;
import java.util.Optional;

public interface MatchRepository {
    List<Match> getAll();
    List<Match> getAllMatchesByPlayerName(String playerName);
    Optional<Match> save(Match match);
}
