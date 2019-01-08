package org.telegram.bot.command;

import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import org.telegram.bot.hibernate.dao.FeedDao;
import org.telegram.bot.hibernate.model.Feed;
import org.telegram.bot.util.MessageUtil;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.net.URL;
import java.sql.Timestamp;

public class AddCommand implements BotCommand {

    @Override
    public void execute(AbsSender sender, Chat chat, User user, String text) {
        try {
            URL url = new URL(text);
            SyndFeed syndFeed = new SyndFeedInput().build(new XmlReader(url));
            FeedDao feedDao = new FeedDao();
            Feed feed = new Feed(
                    chat.getId(),
                    url,
                    syndFeed.getTitle(),
                    new Timestamp(System.currentTimeMillis()));
            if (feedDao.hasExist(feed.getChatId(), url)) {
                String sendText = "You are already subscribed to this feed";
                MessageUtil.sendErrorMessage(sender, chat.getId(), sendText);
            } else {
                feedDao.add(feed);
                String sendText = "Feed <a href='" + feed.getUrl() + "'>"
                        + feed.getTitle() + "</a> successfully added";
                MessageUtil.sendSuccessMessage(sender, chat.getId(), sendText);
            }
        } catch (Exception ex) {
            String sendText = "An error has occured. The feed cannot be added";
            MessageUtil.sendErrorMessage(sender, chat.getId(), sendText);
        }
    }
}
