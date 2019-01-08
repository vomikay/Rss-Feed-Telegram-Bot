package org.telegram.bot.command;

import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

public interface BotCommand {

    void execute(AbsSender sender, Chat chat, User user, String text);
}
