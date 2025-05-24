package ro.mpp2024.persistence.repository;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtils {

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory(){
        if ((sessionFactory == null) || (sessionFactory.isClosed()))
            sessionFactory = createNewSessionFactory();
        return sessionFactory;
    }

    private static SessionFactory createNewSessionFactory() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
        return sessionFactory;
    }

    public static void closeSessionFactory(){
        if (sessionFactory != null)
            sessionFactory.close();
    }
}
