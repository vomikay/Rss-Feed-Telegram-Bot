package org.telegram.bot.command;

import org.telegram.bot.util.MessageUtil;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class HelpCommand implements BotCommand {

    @Override
    public void execute(AbsSender sender, Chat chat, User user, String text) {
        StringBuilder sendTextBuilder = new StringBuilder();
        sendTextBuilder.append("<b>Commands</b>\n")
                .append("/list  List subscribed list\n")
                .append("/add <b>[url]</b>  Add a new feed\n")
                .append("/remove <b>[url]</b>  Remove a feed\n");
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chat.getId());
        sendMessage.setText(sendTextBuilder.toString());
        MessageUtil.sendMessage(sender, chat.getId(), sendTextBuilder.toString());
    }
}
