package org.telegram.bot;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    private static final Logger log = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        try {
            ApiContextInitializer.init();
            TelegramBotsApi api = new TelegramBotsApi();
            api.registerBot(new RssFeedBot());
        } catch (TelegramApiRequestException ex) {
            log.log(Level.SEVERE, "Initialization bot error", ex);
        }
    }
}
