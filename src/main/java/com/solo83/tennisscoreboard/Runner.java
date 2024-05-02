package com.solo83.tennisscoreboard;

import com.solo83.tennisscoreboard.entity.Match;
import com.solo83.tennisscoreboard.entity.Player;
import com.solo83.tennisscoreboard.utils.HibernateUtil;
import org.hibernate.*;

import java.util.List;


public class Runner {

    public static void main(String[] args) {

        Transaction transaction = null;


        Player player = new Player("Daniil Medvedev");
        Player player1 = new Player("Novak Djokovic");

 /*       try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // start a transaction
            transaction = session.beginTransaction();
            // save the student objects
            session.persist(player);
            session.persist(player1);
            // commit transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }*/

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Player> players = session.createQuery("from Player ", Player.class).list();
            List <Match> matches = session.createQuery("from Match ", Match.class).list();
            players.forEach(p -> System.out.println(p.toString()));
            matches.forEach(m -> System.out.println(m.toString()));
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }

    }

}
