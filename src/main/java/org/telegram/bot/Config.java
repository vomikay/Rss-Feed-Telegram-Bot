package org.telegram.bot;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Config {

    private static final Logger log = Logger.getLogger(Config.class.getName());

    public String BOT_TOKEN;
    public String BOT_USERNAME;

    private static Config instance;

    private Config() {
        try (FileInputStream input = new FileInputStream("config.properties")) {
            Properties properties = new Properties();
            properties.load(input);

            BOT_TOKEN = properties.getProperty("bot.token");
            BOT_USERNAME = properties.getProperty("bot.username");
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
