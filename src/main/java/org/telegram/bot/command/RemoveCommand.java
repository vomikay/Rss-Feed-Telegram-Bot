package org.telegram.bot.command;

import org.telegram.bot.hibernate.dao.FeedDao;
import org.telegram.bot.hibernate.dao.SubscriberDao;
import org.telegram.bot.hibernate.entity.Feed;
import org.telegram.bot.hibernate.entity.Subscriber;
import org.telegram.bot.util.MessageUtil;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

public class RemoveCommand implements BotCommand {

    @Override
    public void execute(AbsSender sender, Chat chat, User user, String text) {
        boolean exists = true;
        try {
            URL url = new URL(text);
            SubscriberDao subscriberDao = new SubscriberDao();
            Subscriber subscriber = subscriberDao.get(user.getUserName());
            FeedDao feedDao = new FeedDao();
            Optional<Feed> optionalFeed = subscriber.getFeeds().stream()
                    .filter(f -> f.getUrl().equals(url)).findAny();
            if (optionalFeed.isPresent()) {
                feedDao.remove(optionalFeed.get());
                String sendText = "Feed successfully removed";
                MessageUtil.sendSuccessMessage(sender, chat.getId(), sendText);
            } else {
                exists = false;
            }
        } catch (MalformedURLException e) {
            exists = false;
        }
        if (!exists) {
            String sendText = "You aren't subscribed to this feed";
            MessageUtil.sendErrorMessage(sender, chat.getId(), sendText);
        }
    }
}
