package org.telegram.bot.hibernate.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.telegram.bot.hibernate.model.Feed;
import org.telegram.bot.util.HibernateUtil;

import java.net.URL;
import java.util.List;

public class FeedDao {

    public boolean hasExist(Long chatId, URL url) {
        SessionFactory factory = HibernateUtil.getSessionFactory();
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("from Feed where :chat_id = chat_id and :url = url");
        query.setParameter("chat_id", chatId);
        query.setParameter("url", url);
        List result = query.getResultList();
        session.getTransaction().commit();
        return !result.isEmpty();
    }

    public void remove(Long chatId, URL url) {
        SessionFactory factory = HibernateUtil.getSessionFactory();
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("delete from Feed where :chat_id = chat_id and :url = url");
        query.setParameter("chat_id", chatId);
        query.setParameter("url", url);
        query.executeUpdate();
        session.getTransaction().commit();
    }

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

    public List<Feed> getAll() {
        SessionFactory factory = HibernateUtil.getSessionFactory();
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        Query<Feed> query = session.createQuery("from Feed", Feed.class);
        List<Feed> feeds = query.getResultList();
        session.getTransaction().commit();
        return feeds;
    }
}
