package org.telegram.bot;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.ApiContext;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    private static final Logger log = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        try {
            ApiContextInitializer.init();
            TelegramBotsApi api = new TelegramBotsApi();
            DefaultBotOptions options = ApiContext.getInstance(DefaultBotOptions.class);
            if (!Objects.isNull(Config.getInstance().PROXY_TYPE)) {
                options.setProxyType(Config.getInstance().PROXY_TYPE);
                options.setProxyHost(Config.getInstance().PROXY_HOST);
                options.setProxyPort(Config.getInstance().PROXY_PORT);
            }
            api.registerBot(new RssFeedBot(options));
        } catch (TelegramApiRequestException ex) {
            log.log(Level.SEVERE, "Initialization bot error", ex);
        }
    }
}
