package com.alexkekiy.server.data.repositories;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class HibernateUtils {
    private static HibernateUtils hibernateUtils = null;
    private final EntityManager em;

    private HibernateUtils(EntityManager em) {
        this.em = em;
    }

    public static EntityManager getEntityManager() {
        return (hibernateUtils == null) ?
                (hibernateUtils = createInstance()).em
                : hibernateUtils.em;
    }

    private static HibernateUtils createInstance() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(System.getenv("PERSIST"));
        EntityManager em1 = emf.createEntityManager();
        hibernateUtils = new HibernateUtils(em1);
        return hibernateUtils;
    }
}
