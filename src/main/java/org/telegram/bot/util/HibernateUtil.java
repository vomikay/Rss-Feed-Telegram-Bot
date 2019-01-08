package org.telegram.bot.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.telegram.bot.hibernate.model.Feed;

public class HibernateUtil {

    private static final SessionFactory SESSION_FACTORY = new Configuration()
            .configure()
            .addAnnotatedClass(Feed.class)
            .buildSessionFactory();

    public static SessionFactory getSessionFactory() {
        synchronized (HibernateUtil.class) {
            return SESSION_FACTORY;
        }
    }
}
