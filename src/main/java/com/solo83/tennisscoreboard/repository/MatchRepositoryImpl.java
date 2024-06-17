package com.solo83.tennisscoreboard.repository;
import com.solo83.tennisscoreboard.entity.Match;
import com.solo83.tennisscoreboard.utils.HibernateUtil;
import com.solo83.tennisscoreboard.utils.exception.RepositoryException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

@Slf4j
@Data
public class MatchRepositoryImpl implements MatchRepository {

    private static MatchRepositoryImpl instance;

    private final int RESULTS_PER_PAGE = 3;

    private MatchRepositoryImpl() {
    }

    public static MatchRepositoryImpl getInstance() {
        if (instance == null) {
            instance = new MatchRepositoryImpl();
        }
        return instance;
    }

    @Override
    public List<Match> getAll() throws RepositoryException {
        Transaction transaction;
        List<Match> matches;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Query<Match> query = session.createQuery("from Match", Match.class);
            matches = query.getResultList();
            log.info("Matches per page is {}", matches);
            transaction.commit();
        } catch (Exception e) {
            log.error("Error while getting matches:", e);
            throw new RepositoryException("Error while getting matches");

        }
        return matches;
    }


    public List<Match> getAllMatchesByPlayerName(String playerName) throws RepositoryException {
        playerName = playerName.toLowerCase().trim();
        Transaction transaction;
        List<Match> matches;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Query<Match> query = session.createQuery("from Match m where lower(m.firstPlayer.name) like :playerName or lower(m.secondPlayer.name) like :playerName", Match.class);
            query.setParameter("playerName", "%"+playerName+"%");
            matches = query.getResultList();
            log.info("Matches by PlayerName is: {}", matches);
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
