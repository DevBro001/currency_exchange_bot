package uz.pdp.lesson.bot.handlers;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import uz.pdp.lesson.bot.Bot;

public abstract class BaseHandler {
    protected TelegramBot bot;

    public BaseHandler() {
        this.bot = new TelegramBot(Bot.BOT_TOKEN);
    }

    public abstract void handle(Update update);


}
