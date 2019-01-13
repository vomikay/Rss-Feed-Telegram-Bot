package org.telegram.bot.command;

import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import org.telegram.bot.hibernate.dao.FeedDao;
import org.telegram.bot.hibernate.dao.SubscriberDao;
import org.telegram.bot.hibernate.entity.Feed;
import org.telegram.bot.hibernate.entity.Subscriber;
import org.telegram.bot.util.MessageUtil;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.net.URL;
import java.sql.Timestamp;
import java.util.Set;

public class AddCommand implements BotCommand {

    @Override
    public void execute(AbsSender sender, Chat chat, User user, String text) {
        try {
            URL url = new URL(text);
            SyndFeed syndFeed = new SyndFeedInput().build(new XmlReader(url));
            SubscriberDao subscriberDao = new SubscriberDao();
            Subscriber subscriber = subscriberDao.get(user.getUserName());
            Set<Feed> feeds = subscriber.getFeeds();
            if (feeds.stream().anyMatch(f -> f.getUrl().equals(url))) {
                String sendText = "You are already subscribed to this feed";
                MessageUtil.sendErrorMessage(sender, chat.getId(), sendText);
            } else {
                Feed feed = new Feed(
                        subscriber,
                        url,
                        syndFeed.getTitle(),
                        new Timestamp(System.currentTimeMillis()));
                FeedDao feedDao = new FeedDao();
                feedDao.add(feed);
                String sendText = "Feed <a href='" + feed.getUrl() + "'>"
                        + feed.getTitle() + "</a> successfully added";
                MessageUtil.sendSuccessMessage(sender, chat.getId(), sendText);
            }
        } catch (Exception ex) {
            String sendText = "An error has occured. The feed cannot be added";
            MessageUtil.sendErrorMessage(sender, chat.getId(), sendText);
        }
    }
}
