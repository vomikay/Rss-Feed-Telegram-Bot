package org.telegram.bot.command;

import org.telegram.bot.util.MessageUtil;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class HelpCommand implements BotCommand {

    private static final String SEND_TEXT = "<b>Commands</b>\n"
            + "/list  List subscribed list\n"
            + "/add <b>[url]</b>  Add a new feed\n"
            + "/remove <b>[url]</b>  Remove a feed";

    @Override
    public void execute(AbsSender sender, Chat chat, User user, String text) {
        MessageUtil.sendMessage(sender, chat.getId(), SEND_TEXT);
    }
}
