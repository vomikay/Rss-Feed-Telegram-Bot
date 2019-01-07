package org.telegram.bot.command;

import org.telegram.bot.hibernate.dao.SubscriptionDao;
import org.telegram.bot.hibernate.model.Subscription;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SubscribeCommand implements BotCommand {

    private static final Logger LOG = Logger.getLogger(SubscribeCommand.class.getName());

    @Override
    public void execute(AbsSender sender, Chat chat, User user, String text) {
        try {
            SubscriptionDao subscriptionDao = new SubscriptionDao();
            Subscription subscription = new Subscription(
                    chat.getId(),
                    new URL(text),
                    new Timestamp(System.currentTimeMillis()));
            subscriptionDao.add(subscription);
        } catch (MalformedURLException ex) {
            LOG.log(Level.SEVERE, "Failed to convert string to url", ex);
        }
    }
}
