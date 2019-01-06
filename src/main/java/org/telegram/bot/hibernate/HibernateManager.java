package org.telegram.bot.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.telegram.bot.hibernate.model.Subscription;

public class HibernateManager {

    private static final SessionFactory SESSION_FACTORY = new Configuration()
            .configure()
            .addAnnotatedClass(Subscription.class)
            .buildSessionFactory();

    public static SessionFactory getSessionFactory() {
        synchronized (HibernateManager.class) {
            return SESSION_FACTORY;
        }
    }
}
