package com.solo83.tennisscoreboard.criteriarepository;

import com.solo83.tennisscoreboard.entity.Match;
import com.solo83.tennisscoreboard.entity.Player;
import com.solo83.tennisscoreboard.repository.MatchRepository;
import com.solo83.tennisscoreboard.utils.HibernateUtil;
import com.solo83.tennisscoreboard.utils.exception.RepositoryException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

@Slf4j
public class MatchRepositoryCrit implements MatchRepository {

    private static MatchRepositoryCrit instance;

    private MatchRepositoryCrit() {
    }

    public static MatchRepositoryCrit getInstance() {
        if (instance == null) {
            instance = new MatchRepositoryCrit();
        }
        return instance;
    }

    @Override
    public List<Match> getAll() throws RepositoryException {
        Transaction transaction;
        List<Match> matches;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Match> criteria = builder.createQuery(Match.class);
            Root<Match> root = criteria.from(Match.class);
            criteria.select(root);
            Query<Match> query = session.createQuery(criteria);
            matches = query.getResultList();
            log.info("Matches extracted by Criteria  {}", matches);
            transaction.commit();
        } catch (Exception e) {
            log.error("Error while getting matches:", e);
            throw new RepositoryException("Error while getting matches");

        }
        return matches;
    }

    public List<Match> getAllMatchesByPlayerName(String playerName) throws RepositoryException {
        Transaction transaction;
        List<Match> matches;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Match> criteria = criteriaBuilder.createQuery(Match.class);
            Root<Match> root = criteria.from(Match.class);
            Join<Match, Player> player1Join = root.join("firstPlayer");
            Join<Match, Player> player2Join = root.join("secondPlayer");
            criteria.where
                    (criteriaBuilder.or(
                    (criteriaBuilder.like(criteriaBuilder.lower(player1Join.get("name")), "%"+playerName.toLowerCase().trim()+"%")),
                     criteriaBuilder.like(criteriaBuilder.lower(player2Join.get("name")), "%"+playerName.toLowerCase().trim()+"%")));
            matches = session.createQuery(criteria).getResultList();
            log.info("Matches extracted by Criteria by PlayerName is: {}", matches);
            transaction.commit();
        } catch (Exception e) {
            log.error("Error while getting matches:", e);
            throw new RepositoryException("Error while getting matches");
        }
        return matches;
    }

    @Override
    public Optional<Match> save(Match match) throws RepositoryException {
        Optional<Match> addedMatch;
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            try {
                transaction = session.beginTransaction();
                session.persist(match);
                transaction.commit();
                addedMatch = Optional.of(match);
                log.info("Added match: {}", addedMatch.get());
            } catch (Exception e) {
                log.error("Error while adding match:", e);
                if (transaction != null) {
                    transaction.rollback();
                    log.info("Transaction is {}", transaction.getStatus());
                }
                throw new RepositoryException("Error while adding match");
            }
            return addedMatch;
        }
    }

}
