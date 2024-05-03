package uz.pdp.lesson.bot.handlers;

import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.DeleteMessage;
import com.pengrad.telegrambot.request.EditMessageReplyMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;

import java.util.concurrent.TimeUnit;

public class CallBackQueryHandler extends BaseHandler{

    @Override
    public void handle(Update update) {
        CallbackQuery callbackQuery = update.callbackQuery();
        String data = callbackQuery.data();
        User from = callbackQuery.from();
        Message message = callbackQuery.message();

        bot.execute(new SendMessage(from.id(), "Your call back data is " + data));

        EditMessageReplyMarkup editMessageReplyMarkup = new EditMessageReplyMarkup(from.id(), message.messageId());
        InlineKeyboardButton[][] buttons = new InlineKeyboardButton[1][1];

        buttons[0][0] = new InlineKeyboardButton("Yangi menu").callbackData("MENU");
        InlineKeyboardMarkup replyMarkup = new InlineKeyboardMarkup(buttons);

        editMessageReplyMarkup.replyMarkup(replyMarkup);

        bot.execute(editMessageReplyMarkup);

    }
}
