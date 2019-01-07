package org.telegram.bot.hibernate.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.telegram.bot.hibernate.model.Subscription;
import org.telegram.bot.util.HibernateUtil;

import java.net.URL;
import java.util.List;

public class SubscriptionDao {

    public boolean hasExist(Long chatId, URL url) {
        SessionFactory factory = HibernateUtil.getSessionFactory();
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("from Subscription where :chat_id = chat_id and :url = url");
        query.setParameter("chat_id", chatId);
        query.setParameter("url", url);
        List result = query.getResultList();
        session.getTransaction().commit();
        return !result.isEmpty();
    }

    public void delete(Long chatId, URL url) {
        SessionFactory factory = HibernateUtil.getSessionFactory();
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("delete from Subscription where :chat_id = chat_id and :url = url");
        query.setParameter("chat_id", chatId);
        query.setParameter("url", url);
        query.executeUpdate();
        session.getTransaction().commit();
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
