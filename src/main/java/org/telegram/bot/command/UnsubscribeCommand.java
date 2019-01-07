package org.telegram.bot.command;

import org.telegram.bot.hibernate.dao.SubscriptionDao;
import org.telegram.bot.util.MessageUtil;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.net.MalformedURLException;
import java.net.URL;

public class UnsubscribeCommand implements BotCommand {

    @Override
    public void execute(AbsSender sender, Chat chat, User user, String text) {
        boolean exist = true;
        try {
            URL url = new URL(text);
            Long chatId = chat.getId();
            SubscriptionDao subscriptionDao = new SubscriptionDao();
            if (subscriptionDao.hasExist(chatId, url)) {
                subscriptionDao.delete(chatId, url);
                String sendText = "You successful unsubscribe";
                MessageUtil.sendSuccessMessage(sender, chatId, sendText);
            }
            else {
                exist = false;
            }
        } catch (MalformedURLException e) {
            exist = false;
        }
        if (!exist) {
            String sendText = "You aren't subscribed to the rss";
            MessageUtil.sendErrorMessage(sender, chat.getId(), sendText);
        }
    }
}
