package org.telegram.bot;

import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public class RssFeedBot extends TelegramLongPollingBot {

    public RssFeedBot(DefaultBotOptions options) {
        super(options);
    }

    @Override
    public void onUpdateReceived(Update update) {
    }

    @Override
    public String getBotUsername() {
        return Config.getInstance().BOT_USERNAME;
    }

    @Override
    public String getBotToken() {
        return Config.getInstance().BOT_TOKEN;
    }
}
