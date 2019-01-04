package org.telegram.bot;

import org.telegram.telegrambots.bots.DefaultBotOptions;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Config {

    private static final Logger log = Logger.getLogger(Config.class.getName());

    public String DATABASE_URL;

    public String BOT_TOKEN;
    public String BOT_USERNAME;

    public String PROXY_HOST;
    public Integer PROXY_PORT;
    public DefaultBotOptions.ProxyType PROXY_TYPE;

    private static Config instance;

    private Config() {
        try (FileInputStream input = new FileInputStream("config.properties")) {
            Properties properties = new Properties();
            properties.load(input);

            DATABASE_URL = properties.getProperty("database.url");

            BOT_TOKEN = properties.getProperty("bot.token");
            BOT_USERNAME = properties.getProperty("bot.username");

            if (!Objects.isNull(properties.getProperty("proxy.type"))) {
                PROXY_TYPE = DefaultBotOptions.ProxyType.valueOf(properties.getProperty("proxy.type"));
                PROXY_HOST = properties.getProperty("proxy.host");
                PROXY_PORT = Integer.valueOf(properties.getProperty("proxy.port"));
            }
        } catch (IOException ex) {
            log.log(Level.SEVERE, "Configuration error", ex);
        }
    }

    public static Config getInstance() {
        if (Objects.isNull(instance)) {
            instance = new Config();
        }
        return instance;
    }
}
