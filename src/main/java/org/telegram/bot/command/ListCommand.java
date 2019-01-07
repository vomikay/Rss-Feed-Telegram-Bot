package org.telegram.bot.command;

import org.telegram.bot.hibernate.dao.SubscriptionDao;
import org.telegram.bot.hibernate.model.Subscription;
import org.telegram.bot.util.MessageUtil;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.List;

public class ListCommand implements BotCommand {

    @Override
    public void execute(AbsSender sender, Chat chat, User user, String text) {
        Long chatId = chat.getId();
        SubscriptionDao subscriptionDao = new SubscriptionDao();
        List<Subscription> subscriptions = subscriptionDao.getAll();
        StringBuilder sendTextBuilder = new StringBuilder();
        int number = 1;
        if (!subscriptions.isEmpty()) {
            sendTextBuilder.append("Your subscriptions:\n");
            for (Subscription subscription : subscriptions) {
                sendTextBuilder.append(number)
                        .append(". ")
                        .append("<a href='")
                        .append(subscription.getUrl())
                        .append("'>")
                        .append(subscription.getTitle())
                        .append("</a>\n");
                number++;
            }
            MessageUtil.sendMessage(sender, chatId, sendTextBuilder.toString());
        } else {
            String sendText = "Your subscription list is empty";
            MessageUtil.sendErrorMessage(sender, chatId, sendText);
        }
    }
}
