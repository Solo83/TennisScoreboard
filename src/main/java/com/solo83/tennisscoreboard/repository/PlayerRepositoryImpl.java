package com.solo83.tennisscoreboard.repository;

import com.solo83.tennisscoreboard.entity.Player;
import com.solo83.tennisscoreboard.utils.HibernateUtil;
import com.solo83.tennisscoreboard.utils.exception.RepositoryException;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class PlayerRepositoryImpl implements PlayerRepository {
    private static final Logger log = LoggerFactory.getLogger(PlayerRepositoryImpl.class);
    private static PlayerRepositoryImpl instance;

    private PlayerRepositoryImpl() {
    }

    public static PlayerRepositoryImpl getInstance() {
        if (instance == null) {
            instance = new PlayerRepositoryImpl();
        }
        return instance;
    }

    @Override
    public Optional<Player> getPlayerByName(String playerName) throws RepositoryException {
        Optional<Player> player;
        Transaction transaction;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Query<Player> query = session.createQuery("from Player where name = :playerName", Player.class);
            query.setParameter("playerName", playerName);
            player = Optional.of(query.getSingleResult());
            log.info("Extracted player: {}", player.get());
            transaction.commit();
        } catch (Exception e) {
            log.error("Error while getting player:", e);
            throw new RepositoryException(e.getMessage());
        }
        return player;
    }

    @Override
    public List<Player> getAllPlayers() throws RepositoryException {
        Transaction transaction;
        List<Player> players;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Query<Player> query = session.createQuery("from Player", Player.class);
            players = query.getResultList();
            log.info("Extracted players: {}", players);
            transaction.commit();
        } catch (Exception e) {
            log.error("Error while getting players:", e);
            throw new RepositoryException(e.getMessage());
        }
        return players;
    }

    @Override
    @Transactional
    public Optional<Player>save(Player player) throws RepositoryException {
        Optional<Player> addedPlayer;
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            try {
                transaction = session.beginTransaction();
                session.persist(player);
                transaction.commit();
                addedPlayer = Optional.of(player);
                log.info("Added player: {}", addedPlayer.get());
            } catch (Exception e) {
                log.error("Error while adding player:", e);
                if (transaction != null) {
                    transaction.rollback();
                    log.info("Transaction is {}", transaction.getStatus());
                }
                throw new RepositoryException(e.getMessage());
            }
            return addedPlayer;
        }
    }
}