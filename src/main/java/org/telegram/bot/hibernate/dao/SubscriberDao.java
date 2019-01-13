package org.telegram.bot.hibernate.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.telegram.bot.hibernate.entity.Subscriber;
import org.telegram.bot.util.HibernateUtil;

import java.util.Set;
import java.util.stream.Collectors;

public class SubscriberDao {

    public void add(Subscriber subscriber) {
        SessionFactory factory = HibernateUtil.getSessionFactory();
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        session.save(subscriber);
        session.getTransaction().commit();
    }

    public void update(Subscriber subscriber) {
        SessionFactory factory = HibernateUtil.getSessionFactory();
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        session.update(subscriber);
        session.getTransaction().commit();
    }

    public void remove(Subscriber subscriber) {
        SessionFactory factory = HibernateUtil.getSessionFactory();
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        session.delete(subscriber);
        session.getTransaction().commit();
    }

    public Subscriber get(String userName) {
        SessionFactory factory = HibernateUtil.getSessionFactory();
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        Query<Subscriber> query = session.createQuery(
                "from Subscriber where :username = username", Subscriber.class);
        query.setParameter("username", userName);
        Subscriber subscriber = query.getSingleResult();
        session.getTransaction().commit();
        return subscriber;
    }

    public Set<Subscriber> getAll() {
        SessionFactory factory = HibernateUtil.getSessionFactory();
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        Query<Subscriber> query = session.createQuery("from Subscriber", Subscriber.class);
        Set<Subscriber> subscribers = query.stream().collect(Collectors.toSet());
        session.getTransaction().commit();
        return subscribers;
    }
}
