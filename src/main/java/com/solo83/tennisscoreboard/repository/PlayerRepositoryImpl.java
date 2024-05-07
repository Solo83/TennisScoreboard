package com.solo83.tennisscoreboard.repository;

import com.solo83.tennisscoreboard.entity.Player;
import com.solo83.tennisscoreboard.utils.HibernateUtil;
import com.solo83.tennisscoreboard.utils.exception.RepositoryException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class PlayerRepositoryImpl implements PlayerRepository {
    private static final Logger log = LoggerFactory.getLogger(PlayerRepositoryImpl.class);

    @Override
    public Optional<Player> getPlayer(String playerName) throws RepositoryException {
        Optional<Player> player;
        Transaction transaction = null;
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            Query<Player> query = session.createQuery("from Player where name = :playerName", Player.class);
            query.setParameter("playerName", playerName);
            player = Optional.of(query.getSingleResult());
            log.info("Extracted player: {}", player.get());
            transaction.commit();
            session.close();
        } catch (Exception e) {
            log.error("Error while getting player:", e);
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RepositoryException(e.getMessage());
        }
        return player;
    }

    @Override
    public List<Player> getAllPlayers() throws RepositoryException {
        Transaction transaction = null;
        List<Player> players;
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            Query<Player> query = session.createQuery("from Player", Player.class);
            players = query.getResultList();
            log.info("Extracted players: {}", players);
            transaction.commit();
            session.close();
        } catch (Exception e) {
            log.error("Error while getting players:", e);
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RepositoryException(e.getMessage());
        }
        return players;
    }

    @Override
    public Optional<Player> addPlayer(Player player) throws RepositoryException {
        Optional<Player> addedPlayer;
        Transaction transaction = null;
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.persist(player);
            transaction.commit();
            addedPlayer = Optional.of(player);
            log.info("Added player: {}", addedPlayer.get());
            session.close();
        } catch (Exception e) {
            log.error("Error while adding player:", e);
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RepositoryException(e.getMessage());
        }
        return addedPlayer;
    }
}