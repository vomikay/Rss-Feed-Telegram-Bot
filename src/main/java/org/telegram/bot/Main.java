package org.telegram.bot;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.ApiContext;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    private static final Logger LOG = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        try {
            ApiContextInitializer.init();
            TelegramBotsApi api = new TelegramBotsApi();
            DefaultBotOptions options = ApiContext.getInstance(DefaultBotOptions.class);
            if (Config.PROXY_TYPE != null) {
                options.setProxyType(Config.PROXY_TYPE);
                options.setProxyHost(Config.PROXY_HOST);
                options.setProxyPort(Config.PROXY_PORT);
            }
            api.registerBot(new RssFeedBot(options));
            LOG.info("Bot is ready for work");
        } catch (TelegramApiRequestException ex) {
            LOG.log(Level.SEVERE, "Initialization bot error", ex);
        }
    }
}
