package com.solo83.tennisscoreboard.utils;

import com.solo83.tennisscoreboard.criteriarepository.MatchRepositoryCrit;
import com.solo83.tennisscoreboard.criteriarepository.PlayerRepositoryCrit;
import com.solo83.tennisscoreboard.repository.MatchRepository;
import com.solo83.tennisscoreboard.repository.MatchRepositoryImpl;
import com.solo83.tennisscoreboard.repository.PlayerRepository;
import com.solo83.tennisscoreboard.repository.PlayerRepositoryImpl;
import java.io.FileNotFoundException;

import java.io.InputStream;
import java.util.Properties;

public class RepositoryFactory {


    private Properties getProperties()  {

        Properties prop = new Properties();
        String propFileName = "config.properties";

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName)){

            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }

        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }

        return prop;
    }

    public MatchRepository getMatchRepository() {
        Properties properties = getProperties();
        String matchRepository = properties.getProperty("matchRepository");

        MatchRepositoryCrit matchCriteria = MatchRepositoryCrit.getInstance();
        MatchRepositoryImpl matchStandart = MatchRepositoryImpl.getInstance();

        if (matchRepository.equals("criteria")) {return matchCriteria;}
        else if (matchRepository.equals("standart")) {return matchStandart;}

        return null;

    }

    public PlayerRepository getPlayerRepository() {
        Properties properties = getProperties();
        String playerRepository = properties.getProperty("matchRepository");

        PlayerRepositoryCrit playerCriteria = PlayerRepositoryCrit.getInstance();
        PlayerRepositoryImpl playerStandart = PlayerRepositoryImpl.getInstance();

        if (playerRepository.equals("criteria")) {return playerCriteria;}
        else if (playerRepository.equals("standart")) {return playerStandart;}

        return null;

    }
}
