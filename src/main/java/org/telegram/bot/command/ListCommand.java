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
        SubscriptionDao subscriptionDao = new SubscriptionDao();
        List<Subscription> subscriptions = subscriptionDao.getAll();
        StringBuilder htmlTextBuilder = new StringBuilder();
        int number = 1;
        if (!subscriptions.isEmpty()) {
            htmlTextBuilder.append("Your subscriptions:\n");
            for (Subscription subscription : subscriptions) {
                htmlTextBuilder.append(number)
                        .append(". ")
                        .append("<a href='")
                        .append(subscription.getUrl())
                        .append("'>")
                        .append(subscription.getTitle())
                        .append("</a>\n");
                number++;
            }
            MessageUtil.sendMessage(sender, chat.getId(), htmlTextBuilder.toString());
        } else {
            String sendText = "Your subscription list is empty";
            MessageUtil.sendErrorMessage(sender, chat.getId(), sendText);
        }
    }
}
