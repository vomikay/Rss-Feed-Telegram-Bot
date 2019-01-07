package org.telegram.bot.service;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import org.telegram.bot.hibernate.dao.SubscriptionDao;
import org.telegram.bot.hibernate.model.Subscription;
import org.telegram.bot.util.MessageUtil;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class NotificationService implements Runnable {

    private static final long SLEEP_TIME = 10000;
    private static final Logger LOG = Logger.getLogger(NotificationService.class.getName());

    private final AbsSender sender;

    public NotificationService(AbsSender sender) {
        this.sender = sender;
    }

    @Override
    public void run() {
        while (true) {
            try {
                SubscriptionDao subscriptionDao = new SubscriptionDao();
                List<Subscription> subscriptions = subscriptionDao.getAll();
                for (Subscription subscription : subscriptions) {
                    sendMessage(subscriptionDao, subscription);
                }
                Thread.sleep(SLEEP_TIME);
            } catch (InterruptedException ex) {
                LOG.log(Level.SEVERE, "Interrupt error", ex);
                Thread.currentThread().interrupt();
            }
        }
    }

    private void sendMessage(SubscriptionDao dao, Subscription subscription) {
        try {
            XmlReader xml = new XmlReader(subscription.getUrl());
            SyndFeed feed = new SyndFeedInput().build(xml);
            Timestamp newUpdate = new Timestamp(System.currentTimeMillis());
            List<SyndEntry> entries = getEntries(feed, subscription);

            if (!entries.isEmpty()) {
                String sendText = getSendText(subscription.getTitle(), entries);
                MessageUtil.sendMessage(sender, subscription.getChat(), sendText);
                subscription.setLastUpdate(newUpdate);
                dao.update(subscription);
            }
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, "Failed to read xml", ex);
        } catch (FeedException ex) {
            LOG.log(Level.SEVERE, "Invalid rss format", ex);
        }
    }

    private List<SyndEntry> getEntries(SyndFeed feed, Subscription subscription) {
        return feed.getEntries().stream()
                .filter((e) -> {
                    Date publishDate = e.getUpdatedDate();
                    Timestamp publish = new Timestamp(publishDate.getTime());
                    return subscription.getLastUpdate().before(publish);
                })
                .collect(Collectors.toList());
    }

    private String getSendText(String title, List<SyndEntry> entries) {
        StringBuilder sendTextBuilder = new StringBuilder();
        sendTextBuilder.append("<b>")
                .append(title)
                .append("</b>\n");
        for (SyndEntry feedEntry : entries) {
            sendTextBuilder.append("<a href='")
                    .append(feedEntry.getLink())
                    .append("'>")
                    .append(feedEntry.getTitle())
                    .append("</a>\n");
        }
        return sendTextBuilder.toString();
    }
}