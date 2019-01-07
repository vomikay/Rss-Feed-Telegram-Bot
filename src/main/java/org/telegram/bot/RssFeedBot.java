package org.telegram.bot;

import org.telegram.bot.command.BotCommand;
import org.telegram.bot.command.ListCommand;
import org.telegram.bot.command.SubscribeCommand;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class RssFeedBot extends TelegramLongPollingBot {

    private final Map<String, BotCommand> commands = new HashMap<String, BotCommand>() {{
        put("list", new ListCommand());
        put("subscribe", new SubscribeCommand());
    }};

    public RssFeedBot(DefaultBotOptions options) {
        super(options);
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            if (message.isCommand()) {
                String command = getCommand(message.getText());
                String text = getCommandMessage(message.getText());
                Chat chat = message.getChat();
                User user = message.getFrom();
                if (commands.containsKey(command)) {
                    commands.get(command).execute(this, chat, user, text);
                }
            }
        }
    }

    @Override
    public String getBotUsername() {
        return Config.BOT_USERNAME;
    }

    @Override
    public String getBotToken() {
        return Config.BOT_TOKEN;
    }

    private String getCommand(String text) {
        String[] commandSplit = text.substring(1).split("\\s+");
        return commandSplit[0];
    }

    private String getCommandMessage(String text) {
        String[] commandSplit = text.substring(1).split("\\s+");
        String[] textSplit = Arrays.copyOfRange(commandSplit, 1, commandSplit.length);
        return String.join(" ", textSplit);
    }
}
