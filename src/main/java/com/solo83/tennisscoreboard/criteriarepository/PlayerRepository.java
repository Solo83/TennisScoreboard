package com.solo83.tennisscoreboard.criteriarepository;

import com.solo83.tennisscoreboard.entity.Player;
import com.solo83.tennisscoreboard.utils.HibernateUtil;
import com.solo83.tennisscoreboard.utils.exception.RepositoryException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;


import java.util.Optional;

@Slf4j
public class PlayerRepository {
    private static PlayerRepository instance;

    private PlayerRepository() {
    }

    public static PlayerRepository getInstance() {
        if (instance == null) {
            instance = new PlayerRepository();
        }
        return instance;
    }

    public Optional<Player> getPlayerByName(String playerName) throws RepositoryException {
        Optional<Player> player;
        Transaction transaction;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Player> critQuery = builder.createQuery(Player.class);
            Root<Player> root = critQuery.from(Player.class);
            critQuery.select(root);
            critQuery.where(builder.equal(root.get("name"), playerName));
            Query<Player> query = session.createQuery(critQuery);
            player = query.uniqueResultOptional();
            log.info("Player extracted by CriteriaBuilder: {}",player.get());
            transaction.commit();
        } catch (Exception e) {
            log.error("Error while getting player:", e);
            throw new RepositoryException("Error while getting player by Name");
        }
        return player;
    }

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
                throw new RepositoryException("Error while adding player");
            }
            return addedPlayer;
        }
    }
}
