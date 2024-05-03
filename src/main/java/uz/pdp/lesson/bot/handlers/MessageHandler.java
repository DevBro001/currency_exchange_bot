package uz.pdp.lesson.bot.handlers;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;

public class MessageHandler extends BaseHandler{

    @Override
    public void handle(Update update) {
        Message message = update.message();
        User from = message.from();
        SendMessage sendMessage = new SendMessage(from.id(), "Sonlardan birini tanla:");
        InlineKeyboardButton[][] buttons = new InlineKeyboardButton[2][3];
        buttons[0][0] = new InlineKeyboardButton("Bir").callbackData("1");
        buttons[0][1] = new InlineKeyboardButton("Ikki").callbackData("2");
        buttons[0][2] = new InlineKeyboardButton("Uch").callbackData("3");
        buttons[1][0] = new InlineKeyboardButton("To`rt").callbackData("4");
        buttons[1][1] = new InlineKeyboardButton("Besh").callbackData("5");
        buttons[1][2] = new InlineKeyboardButton("Olti").callbackData("6");

        InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup(buttons);
        sendMessage.replyMarkup(inlineKeyboard);
        bot.execute(sendMessage);

    }
}
