package org.telegram.bot.command;

import org.telegram.bot.util.MessageUtil;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class StartCommand implements BotCommand {

    private static final String SEND_TEXT = "Hi! \u270B\n"
            + "I am a RSS and atom channels reader. Give me your feeds\n"
            + "URLs and I will send you notifications as soon as news\n"
            + "are available.";

    private HelpCommand helpCommand;

    public StartCommand(HelpCommand helpCommand) {
        this.helpCommand = helpCommand;
    }

    @Override
    public void execute(AbsSender sender, Chat chat, User user, String text) {
        MessageUtil.sendMessage(sender, chat.getId(), SEND_TEXT);
        helpCommand.execute(sender, chat, user, text);
    }
}
