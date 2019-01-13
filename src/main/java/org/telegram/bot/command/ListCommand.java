package org.telegram.bot.command;

import org.telegram.bot.hibernate.dao.SubscriberDao;
import org.telegram.bot.hibernate.entity.Feed;
import org.telegram.bot.hibernate.entity.Subscriber;
import org.telegram.bot.util.MessageUtil;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.Set;

public class ListCommand implements BotCommand {

    @Override
    public void execute(AbsSender sender, Chat chat, User user, String text) {
        SubscriberDao subscriberDao = new SubscriberDao();
        Subscriber subscriber = subscriberDao.get(user.getUserName());
        Set<Feed> feeds = subscriber.getFeeds();
        StringBuilder sendTextBuilder = new StringBuilder();
        int number = 1;
        if (!feeds.isEmpty()) {
            sendTextBuilder.append("<b>Your have ");
            sendTextBuilder.append(feeds.size());
            sendTextBuilder.append(" feed subscriptions</b>\n");
            for (Feed feed : feeds) {
                sendTextBuilder.append(number)
                        .append(". ")
                        .append("<a href='")
                        .append(feed.getUrl())
                        .append("'>")
                        .append(feed.getTitle())
                        .append("</a>\n");
                number++;
            }
            MessageUtil.sendMessage(sender, chat.getId(), sendTextBuilder.toString());
        } else {
            String sendText = "You have no subscriptions";
            MessageUtil.sendErrorMessage(sender, chat.getId(), sendText);
        }
    }
}
