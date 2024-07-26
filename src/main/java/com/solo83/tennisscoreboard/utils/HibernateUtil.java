package com.solo83.tennisscoreboard.utils;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

@Slf4j
@UtilityClass
public class HibernateUtil {
    private StandardServiceRegistry registry;
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                registry = new StandardServiceRegistryBuilder().configure().build();
                MetadataSources sources = new MetadataSources(registry);
                Metadata metadata = sources.getMetadataBuilder().build();
                sessionFactory = metadata.getSessionFactoryBuilder().build();
                log.info("Hibernate SessionFactory created");
            } catch (Exception e) {
                log.error(e.getMessage());
                shutdown();
            }
        }
        return sessionFactory;
    }

    public void shutdown() {
        if (registry != null) {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}