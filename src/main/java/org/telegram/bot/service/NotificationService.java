package org.telegram.bot.service;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import org.telegram.bot.hibernate.dao.FeedDao;
import org.telegram.bot.hibernate.dao.SubscriberDao;
import org.telegram.bot.hibernate.entity.Feed;
import org.telegram.bot.hibernate.entity.Subscriber;
import org.telegram.bot.util.MessageUtil;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Set;
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
                SubscriberDao subscriberDao = new SubscriberDao();
                Set<Subscriber> subscribers = subscriberDao.getAll();
                for (Subscriber subscriber : subscribers) {
                    for (Feed feed : subscriber.getFeeds()) {
                        sendMessage(feed, subscriber.getChatId());
                    }
                }
                Thread.sleep(SLEEP_TIME);
            } catch (InterruptedException ex) {
                LOG.log(Level.SEVERE, "Interrupt error", ex);
                Thread.currentThread().interrupt();
            }
        }
    }

    private void sendMessage(Feed feed, Long chatId) {
        try {
            FeedDao feedDao = new FeedDao();
            XmlReader xml = new XmlReader(feed.getUrl());
            SyndFeed syndFeed = new SyndFeedInput().build(xml);
            Timestamp newUpdate = new Timestamp(System.currentTimeMillis());
            List<SyndEntry> entries = getEntries(syndFeed, feed);

            if (!entries.isEmpty()) {
                String sendText = getSendText(feed.getTitle(), entries);
                MessageUtil.sendMessage(sender, chatId, sendText);
                feed.setLastUpdate(newUpdate);
                feedDao.update(feed);
            }
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, "Failed to read xml", ex);
        } catch (FeedException ex) {
            LOG.log(Level.SEVERE, "Invalid rss or atom format", ex);
        }
    }

    private List<SyndEntry> getEntries(SyndFeed syndFeed, Feed feed) {
        return syndFeed.getEntries().stream()
                .filter((e) -> {
                    Date publishDate = e.getUpdatedDate();
                    Timestamp publish = new Timestamp(publishDate.getTime());
                    return feed.getLastUpdate().before(publish);
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