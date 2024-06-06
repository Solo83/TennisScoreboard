package com.solo83.tennisscoreboard.repository;

import com.solo83.tennisscoreboard.dto.MatchScoreModel;

import java.util.UUID;

public interface OngoingMatchesRepository {
    void save(UUID uuid, MatchScoreModel matchScoreModel);
    void remove(UUID uuid);
    MatchScoreModel get(UUID uuid);
}
