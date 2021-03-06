package org.telegram.bot.hibernate.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.telegram.bot.hibernate.entity.Feed;
import org.telegram.bot.hibernate.entity.Subscriber;
import org.telegram.bot.util.HibernateUtil;

import java.net.URL;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class FeedDao {

    public void add(Feed feed) {
        SessionFactory factory = HibernateUtil.getSessionFactory();
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        session.save(feed);
        session.getTransaction().commit();
    }

    public void update(Feed feed) {
        SessionFactory factory = HibernateUtil.getSessionFactory();
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        session.update(feed);
        session.getTransaction().commit();
    }

    public void remove(Feed feed) {
        SessionFactory factory = HibernateUtil.getSessionFactory();
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        session.delete(feed);
        session.getTransaction().commit();
    }

    public Set<Feed> getAll() {
        SessionFactory factory = HibernateUtil.getSessionFactory();
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        Query<Feed> query = session.createQuery("from Feed", Feed.class);
        Set<Feed> feeds = query.stream().collect(Collectors.toSet());
        session.getTransaction().commit();
        return feeds;
    }

    public boolean hasExists(Subscriber subscriber, URL url) {
        SessionFactory factory = HibernateUtil.getSessionFactory();
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("from Subscriber where :subscriber = subscriber and :url = url");
        query.setParameter("subscriber", subscriber);
        query.setParameter("url", url);
        List result = query.getResultList();
        session.getTransaction().commit();
        return !result.isEmpty();
    }
}
