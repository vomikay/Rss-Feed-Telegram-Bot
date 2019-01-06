package org.telegram.bot;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.telegram.bot.hibernate.HibernateManager;
import org.telegram.bot.hibernate.dao.SubscriptionDao;
import org.telegram.bot.hibernate.model.Subscription;
import org.telegram.bot.service.NotificationService;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RssFeedBot extends TelegramLongPollingBot {

    private static final Logger LOG = Logger.getLogger(RssFeedBot.class.getName());

    public RssFeedBot(DefaultBotOptions options) {
        super(options);
        NotificationService notificationService = new NotificationService(this);
        Thread notificationServiceThread = new Thread(notificationService);
        notificationServiceThread.start();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() &&  update.getMessage().hasText()) {
            try {
                Message message = update.getMessage();
                Subscription subscription = new Subscription(
                        message.getChatId(),
                        new URL(message.getText()),
                        new Timestamp(System.currentTimeMillis()));
                SubscriptionDao subscriptionDao = new SubscriptionDao();
                subscriptionDao.add(subscription);
            } catch (MalformedURLException ex) {
                LOG.log(Level.SEVERE, "Failed to convert string to url", ex);
            }
        }
    }

    @Override
    public String getBotUsername() {
        return Config.BOT_USERNAME;
    }

    @Override
    public String getBotToken() {
        return Config.BOT_TOKEN;
    }
}
