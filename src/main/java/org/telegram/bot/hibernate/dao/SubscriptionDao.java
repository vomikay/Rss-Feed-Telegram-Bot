package org.telegram.bot.hibernate.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.telegram.bot.hibernate.HibernateManager;
import org.telegram.bot.hibernate.model.Subscription;

public class SubscriptionDao {

    public void add(Subscription subscription) {
        SessionFactory factory = HibernateManager.getSessionFactory();
        try (Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            session.save(subscription);
            session.getTransaction().commit();
        }
    }
}
