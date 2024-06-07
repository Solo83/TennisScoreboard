package com.solo83.tennisscoreboard.repository;

import com.solo83.tennisscoreboard.dto.OngoingMatch;

import java.util.UUID;

public interface OngoingMatchesRepository {
    void save(UUID uuid, OngoingMatch ongoingMatch);
    void remove(UUID uuid);
    OngoingMatch get(UUID uuid);
}
