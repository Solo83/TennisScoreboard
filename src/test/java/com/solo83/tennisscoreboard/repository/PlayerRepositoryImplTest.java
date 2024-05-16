package com.solo83.tennisscoreboard.repository;

import com.solo83.tennisscoreboard.utils.exception.RepositoryException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PlayerRepositoryImplTest {
    private final PlayerRepository repository = PlayerRepositoryImpl.getInstance();

    @Test
    void transaction_rollingBack_whenException_whileAddingPlayerAttempt() {

        Assertions.assertThrows(RepositoryException.class,
                () -> repository.save(null));


    }

}