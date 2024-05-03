package com.solo83.tennisscoreboard.repository;

import com.solo83.tennisscoreboard.entity.Player;
import com.solo83.tennisscoreboard.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PlayerRepositoryImpl implements PlayerRepository {
    private static final Logger log = LoggerFactory.getLogger(PlayerRepositoryImpl.class);

    @Override
    public Optional<Player> getPlayer(String playerName) {
        Optional<Player> player = Optional.empty();
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Query<Player> query = session.createQuery("from Player where name = :playerName", Player.class);
            query.setParameter("playerName", playerName);
            player = Optional.of(query.getSingleResult());
            log.info("Extracted player: {}", player.get());
            transaction.commit();
        } catch (Exception e) {
            log.error("Error while getting player:", e);
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return player;
    }

    @Override
    public List<Player> getAllPlayers() {
        Transaction transaction = null;
        List<Player> players = new ArrayList<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Query<Player> query = session.createQuery("from Player", Player.class);
            players = query.getResultList();
            log.info("Extracted players: {}", players);
            transaction.commit();
        } catch (Exception e) {
            log.error("Error while getting players:", e);
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return players;
    }

    @Override
    public Optional<Player> addPlayer(Player player) {
        Optional<Player> addedPlayer = Optional.empty();
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(player);
            addedPlayer = Optional.of(session.get(Player.class, player.getId()));
            log.info("Added player: {}", addedPlayer.get());
            transaction.commit();
        } catch (Exception e) {
            log.error("Error while adding player:", e);
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return addedPlayer;
    }
}