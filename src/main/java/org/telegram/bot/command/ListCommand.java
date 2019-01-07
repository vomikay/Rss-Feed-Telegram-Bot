package org.telegram.bot.command;

import org.telegram.bot.hibernate.dao.SubscriptionDao;
import org.telegram.bot.hibernate.model.Subscription;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ListCommand implements BotCommand {

    private static final Logger LOG = Logger.getLogger(ListCommand.class.getName());

    @Override
    public void execute(AbsSender sender, Chat chat, User user, String text) {
        try {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setParseMode("html");
            sendMessage.setChatId(chat.getId());
            sendMessage.setText(getMessageText());
            sender.execute(sendMessage);
        } catch (TelegramApiException ex) {
            LOG.log(Level.SEVERE, "Failed to send message", ex);
        }
    }

    private String getMessageText() {
        SubscriptionDao subscriptionDao = new SubscriptionDao();
        List<Subscription> subscriptions = subscriptionDao.getAll();
        StringBuilder htmlText = new StringBuilder();
        int number = 1;
        for (Subscription subscription : subscriptions) {
            htmlText.append(number)
                    .append(". ")
                    .append("<a href='")
                    .append(subscription.getUrl())
                    .append("'>")
                    .append(subscription.getUrl())
                    .append("</a>\n");
            number++;
        }
        return htmlText.toString();
    }
}
