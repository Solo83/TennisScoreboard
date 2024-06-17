package com.solo83.tennisscoreboard.repository.criteriarepository;

import com.solo83.tennisscoreboard.entity.Player;
import com.solo83.tennisscoreboard.repository.PlayerRepository;
import com.solo83.tennisscoreboard.utils.HibernateUtil;
import com.solo83.tennisscoreboard.utils.exception.RepositoryException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;


import java.util.List;
import java.util.Optional;

@Slf4j
public class PlayerRepositoryCrit implements PlayerRepository {
    private static PlayerRepositoryCrit instance;

    private PlayerRepositoryCrit() {
    }

    public static PlayerRepositoryCrit getInstance() {
        if (instance == null) {
            instance = new PlayerRepositoryCrit();
        }
        return instance;
    }

    @Override
    public Optional<Player> getByName(String playerName) throws RepositoryException {
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

    @Override
    public List<Player> getAll() throws RepositoryException {
        Transaction transaction;
        List<Player> players;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Player> criteria = builder.createQuery(Player.class);
            Root<Player> root = criteria.from(Player.class);
            criteria.select(root);
            Query<Player> query = session.createQuery(criteria);
            players = query.getResultList();
            log.info("Extracted players from Criteria: {}", players);
            transaction.commit();
        } catch (Exception e) {
            log.error("Error while getting players:", e);
            throw new RepositoryException("Error while getting players");

        }
        return players;
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
