package org.telegram.bot.command;

import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import org.telegram.bot.hibernate.dao.SubscriptionDao;
import org.telegram.bot.hibernate.model.Subscription;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.net.URL;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SubscribeCommand implements BotCommand {

    private static final Logger LOG = Logger.getLogger(SubscribeCommand.class.getName());

    @Override
    public void execute(AbsSender sender, Chat chat, User user, String text) {
        try {
            URL url = new URL(text);
            new SyndFeedInput().build(new XmlReader(url));
            SubscriptionDao subscriptionDao = new SubscriptionDao();
            Subscription subscription = new Subscription(
                    chat.getId(),
                    new URL(text),
                    new Timestamp(System.currentTimeMillis()));
            subscriptionDao.add(subscription);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Invalid rss link", ex);
            try {
                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(chat.getId());
                sendMessage.setText("Sorry! It isn't rss link");
                sender.execute(sendMessage);
            } catch (TelegramApiException e) {
                LOG.log(Level.SEVERE, "Failed to send message");
            }
        }
    }
}
