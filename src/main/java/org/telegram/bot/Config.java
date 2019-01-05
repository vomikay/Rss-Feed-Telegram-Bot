package org.telegram.bot;

import org.telegram.telegrambots.bots.DefaultBotOptions;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Config {

    public static final DefaultBotOptions.ProxyType PROXY_TYPE;
    public static final String PROXY_HOST;
    public static final Integer PROXY_PORT;
    public static final String BOT_TOKEN;
    public static final String BOT_USERNAME;
    public static final String DATABASE_URL;

    private static final Logger LOG = Logger.getLogger(Config.class.getName());

    static {
        try (FileInputStream input = new FileInputStream("src/main/resources/config.properties")) {
            Properties properties = new Properties();
            properties.load(input);
            PROXY_TYPE = DefaultBotOptions.ProxyType.valueOf(properties.getProperty("proxy.type"));
            PROXY_HOST = properties.getProperty("proxy.host");
            PROXY_PORT = Integer.valueOf(properties.getProperty("proxy.port"));
            BOT_TOKEN = properties.getProperty("bot.token");
            BOT_USERNAME = properties.getProperty("bot.username");
            DATABASE_URL = properties.getProperty("database.url");
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, "Failed to configure application", ex);
            throw new ExceptionInInitializerError();
        }
    }
}
