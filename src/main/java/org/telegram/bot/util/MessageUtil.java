package org.telegram.bot.util;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MessageUtil {

    private static final Logger LOG = Logger.getLogger(MessageUtil.class.getName());

    public static void sendMessage(AbsSender sender, Long chatId, String htmlText) {
        try {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setParseMode("html");
            sendMessage.setChatId(chatId);
            sendMessage.setText(htmlText);
            sender.execute(sendMessage);
        } catch (TelegramApiException ex) {
            LOG.log(Level.SEVERE, "Failed to send message", ex);
        }
    }
}
