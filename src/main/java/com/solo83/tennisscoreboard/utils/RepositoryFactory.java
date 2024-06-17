package com.solo83.tennisscoreboard.utils;

import com.solo83.tennisscoreboard.repository.criteriarepository.MatchRepositoryCrit;
import com.solo83.tennisscoreboard.repository.criteriarepository.PlayerRepositoryCrit;
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

        return (matchRepository.equals("criteria")) ? MatchRepositoryCrit.getInstance() : MatchRepositoryImpl.getInstance();

    }

    public PlayerRepository getPlayerRepository() {
        Properties properties = getProperties();
        String playerRepository = properties.getProperty("matchRepository");

        return (playerRepository.equals("criteria")) ?  PlayerRepositoryCrit.getInstance() : PlayerRepositoryImpl.getInstance();

    }
}
