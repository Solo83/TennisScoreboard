package com.solo83.tennisscoreboard.repository;

import com.solo83.tennisscoreboard.utils.RepositoryFactory;
import com.solo83.tennisscoreboard.utils.exception.RepositoryException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PlayerRepositoryImplTest {
    private final PlayerRepository repository = RepositoryFactory.getPlayerRepository();

    @Test
    void transactionRollingBackWhenExceptionWhileAddingPlayerAttempt() {

        Assertions.assertThrows(RepositoryException.class,
                () -> repository.save(null));


    }

}