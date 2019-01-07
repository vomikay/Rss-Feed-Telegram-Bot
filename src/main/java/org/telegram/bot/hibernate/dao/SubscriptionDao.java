package org.telegram.bot.hibernate.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.telegram.bot.hibernate.model.Subscription;
import org.telegram.bot.util.HibernateUtil;

import java.util.List;

public class SubscriptionDao {

    public boolean hasExisted(Subscription subscription) {
        SessionFactory factory = HibernateUtil.getSessionFactory();
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("from Subscription where :chat = chat and :url = url");
        query.setParameter("chat", subscription.getChat());
        query.setParameter("url", subscription.getUrl());
        List result = query.getResultList();
        session.getTransaction().commit();
        return !result.isEmpty();
    }

    public void add(Subscription subscription) {
        SessionFactory factory = HibernateUtil.getSessionFactory();
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        session.save(subscription);
        session.getTransaction().commit();
    }

    public void update(Subscription subscription) {
        SessionFactory factory = HibernateUtil.getSessionFactory();
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        session.update(subscription);
        session.getTransaction().commit();
    }

    public List<Subscription> getAll() {
        SessionFactory factory = HibernateUtil.getSessionFactory();
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        Query<Subscription> query = session.createQuery("from Subscription", Subscription.class);
        List<Subscription> subscriptions = query.getResultList();
        session.getTransaction().commit();
        return subscriptions;
    }
}
