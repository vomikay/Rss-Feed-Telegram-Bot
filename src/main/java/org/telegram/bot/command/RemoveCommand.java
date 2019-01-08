package org.telegram.bot.command;

import org.telegram.bot.hibernate.dao.FeedDao;
import org.telegram.bot.util.MessageUtil;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.net.MalformedURLException;
import java.net.URL;

public class RemoveCommand implements BotCommand {

    @Override
    public void execute(AbsSender sender, Chat chat, User user, String text) {
        boolean exists = true;
        try {
            URL url = new URL(text);
            Long chatId = chat.getId();
            FeedDao feedDao = new FeedDao();
            if (feedDao.hasExist(chatId, url)) {
                feedDao.remove(chatId, url);
                String sendText = "Feed successfully removed";
                MessageUtil.sendSuccessMessage(sender, chatId, sendText);
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
