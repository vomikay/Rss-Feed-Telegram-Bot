package org.telegram.bot.service;

import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.logging.Level;
import java.util.logging.Logger;

public class NotificationService implements Runnable {

    private static final long SLEEP_TIME = 5000;
    private static final Logger LOG = Logger.getLogger(NotificationService.class.getName());

    private final AbsSender sender;

    public NotificationService(AbsSender sender) {
        this.sender = sender;
    }

    @Override
    public void run() {
        while (true) {
            try {
                // Do something
                Thread.sleep(SLEEP_TIME);
            } catch (InterruptedException ex) {
                LOG.log(Level.SEVERE,"Interrupted error", ex);
                Thread.currentThread().interrupt();
            }
        }
    }
}
