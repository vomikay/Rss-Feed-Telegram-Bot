package org.telegram.bot.command;

import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import org.telegram.bot.hibernate.dao.SubscriptionDao;
import org.telegram.bot.hibernate.model.Subscription;
import org.telegram.bot.util.MessageUtil;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.net.URL;
import java.sql.Timestamp;

public class SubscribeCommand implements BotCommand {

    @Override
    public void execute(AbsSender sender, Chat chat, User user, String text) {
        try {
            URL url = new URL(text);
            SyndFeed feed = new SyndFeedInput().build(new XmlReader(url));
            SubscriptionDao subscriptionDao = new SubscriptionDao();
            Subscription subscription = new Subscription(
                    chat.getId(),
                    url,
                    feed.getTitle(),
                    new Timestamp(System.currentTimeMillis()));
            if (subscriptionDao.hasExist(subscription.getChat(), url)) {
                String sendText = "You are already subscribed to the rss";
                MessageUtil.sendErrorMessage(sender, chat.getId(), sendText);
            } else {
                subscriptionDao.add(subscription);
                String sendText = "You subscribe to <a href='" + subscription.getUrl() + "'>"
                        + subscription.getTitle() + "</a>";
                MessageUtil.sendSuccessMessage(sender, chat.getId(), sendText);
            }
        } catch (Exception ex) {
            String sendText = "Sorry! It isn't rss link";
            MessageUtil.sendErrorMessage(sender, chat.getId(), sendText);
        }
    }
}
