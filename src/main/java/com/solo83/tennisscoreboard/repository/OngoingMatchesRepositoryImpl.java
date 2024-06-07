package com.solo83.tennisscoreboard.repository;

import com.solo83.tennisscoreboard.dto.OngoingMatch;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class OngoingMatchesRepositoryImpl implements OngoingMatchesRepository {

    private static OngoingMatchesRepositoryImpl instance;

    private OngoingMatchesRepositoryImpl() {
    }

    public static OngoingMatchesRepositoryImpl getInstance() {
        if (instance == null) {
            instance = new OngoingMatchesRepositoryImpl();
        }
        return instance;
    }

    private final Map<UUID, OngoingMatch> matches = new ConcurrentHashMap<>();

    public void save(UUID uuid, OngoingMatch ongoingMatch) {
        matches.put(uuid, ongoingMatch);
    }

    public OngoingMatch get(UUID uuid) {
        return matches.get(uuid);
    }

    public void remove (UUID uuid)  {
        matches.remove(uuid);
    }

}
