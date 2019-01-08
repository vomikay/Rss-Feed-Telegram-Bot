package org.telegram.bot.command;

import org.telegram.bot.hibernate.dao.FeedDao;
import org.telegram.bot.hibernate.model.Feed;
import org.telegram.bot.util.MessageUtil;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.List;

public class ListCommand implements BotCommand {

    @Override
    public void execute(AbsSender sender, Chat chat, User user, String text) {
        Long chatId = chat.getId();
        FeedDao feedDao = new FeedDao();
        List<Feed> feeds = feedDao.getAll();
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
            MessageUtil.sendMessage(sender, chatId, sendTextBuilder.toString());
        } else {
            String sendText = "You have no subscriptions";
            MessageUtil.sendErrorMessage(sender, chatId, sendText);
        }
    }
}
