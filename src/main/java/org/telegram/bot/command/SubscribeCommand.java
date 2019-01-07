package org.telegram.bot.command;

import com.rometools.rome.feed.synd.SyndFeed;
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
            SyndFeed feed = new SyndFeedInput().build(new XmlReader(url));
            SubscriptionDao subscriptionDao = new SubscriptionDao();
            Subscription subscription = new Subscription(
                    chat.getId(),
                    new URL(text),
                    new Timestamp(System.currentTimeMillis()));
            subscriptionDao.add(subscription);
            String sendText = "You subscribed to <b>" + feed.getTitle() + "</b>";
            sendMessage(sender, chat, sendText);
        } catch (Exception ex) {
            String sendText = "Sorry! It isn't rss link";
            sendMessage(sender, chat, sendText);
        }
    }

    private void sendMessage(AbsSender sender, Chat chat, String text) {
        try {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setParseMode("html");
            sendMessage.setChatId(chat.getId());
            sendMessage.setText(text);
            sender.execute(sendMessage);
        } catch (TelegramApiException ex) {
            LOG.log(Level.SEVERE, "Failed to send message", ex);
        }
    }
}
