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

    private static final Logger LOG = Logger.getLogger(Config.class.getName());

    static {
        try (FileInputStream input = new FileInputStream("src/main/resources/config.properties")) {
            Properties properties = new Properties();
            properties.load(input);
            String proxyTypeString = properties.getProperty("proxy.type");
            String proxyPortString = properties.getProperty("proxy.port");
            PROXY_TYPE = (proxyTypeString == null) ? null : DefaultBotOptions.ProxyType.valueOf(proxyTypeString);
            PROXY_HOST = properties.getProperty("proxy.host");
            PROXY_PORT = (proxyPortString == null) ? null : Integer.valueOf(proxyPortString);
            BOT_TOKEN = properties.getProperty("bot.token");
            BOT_USERNAME = properties.getProperty("bot.username");
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, "Failed to configure application", ex);
            throw new ExceptionInInitializerError();
        }
    }
}
