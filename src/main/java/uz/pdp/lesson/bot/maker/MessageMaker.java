package uz.pdp.lesson.bot.maker;

import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import uz.pdp.lesson.backend.model.Archive;
import uz.pdp.lesson.backend.model.MyUser;
import uz.pdp.lesson.bot.states.base.BaseState;
import uz.pdp.lesson.bot.states.child.ArchiveState;

public class MessageMaker {

    public SendMessage mainMenu(MyUser curUser){
        SendMessage sendMessage = new SendMessage(curUser.getId(), "Main Menu");
        InlineKeyboardButton[][] buttons = {
                {
                        new InlineKeyboardButton("Archive").callbackData("ARCHIVE"),
                        new InlineKeyboardButton("Calculate").callbackData("CALCULATE"),
                }
        };
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup(buttons);
        sendMessage.replyMarkup(markup);
        return sendMessage;
    }
    public SendMessage enterDateForArchive(MyUser curUser){
        SendMessage sendMessage = new SendMessage(curUser.getId(), "Enter Date (dd/mm/yyyy):");
        InlineKeyboardButton[][] buttons = {
                {
                        new InlineKeyboardButton("Back").callbackData("BACK")
                }
        };
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup(buttons);
        sendMessage.replyMarkup(markup);
        return sendMessage;
    }
     public SendMessage showResultForArchive(MyUser curUser, Archive archive){
        String text = """
                Result:
                    Date: %s
                    Currency: %s
                    Rate: %s
                """.formatted(archive.getDate(),archive.getCurrency(),archive.getRate());



        SendMessage sendMessage = new SendMessage(curUser.getId(), text);
        InlineKeyboardButton[][] buttons = {
                {
                        new InlineKeyboardButton("Back").callbackData("BACK"),
                        new InlineKeyboardButton("Main Manu").callbackData("BACK_TO_MAIN_MENU")
                }
        };
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup(buttons);
        sendMessage.replyMarkup(markup);
        return sendMessage;
    }

    public SendMessage enterPhoneNumber(MyUser curUser){
        SendMessage sendMessage = new SendMessage(curUser.getId(), "Enter Phone Number");
        KeyboardButton[][] buttons ={
                { new KeyboardButton("Phone Number").requestContact(true) }
        };
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(buttons).oneTimeKeyboard(true).resizeKeyboard(true);
        sendMessage.replyMarkup(replyKeyboardMarkup);
        return sendMessage;
    }
    public SendMessage chooseCurrency(MyUser curUser) {
        SendMessage sendMessage = new SendMessage(curUser.getId(), "Choose currency: ");
        InlineKeyboardButton[][] buttons = {
                {
                        new InlineKeyboardButton("USD \uD83C\uDDFA\uD83C\uDDF8").callbackData("USD"),
                        new InlineKeyboardButton("RUB \uD83C\uDDF7\uD83C\uDDFA").callbackData("RUB"),
                },
                {
                        new InlineKeyboardButton("EUR \uD83C\uDDEA\uD83C\uDDFA").callbackData("EUR"),
                        new InlineKeyboardButton("JPY \uD83C\uDDEF\uD83C\uDDF5").callbackData("JPY"),
                },
                {
                        new InlineKeyboardButton("BACK").callbackData("BACK")
                }
        };
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup(buttons);
        sendMessage.replyMarkup(markup);
        return sendMessage;
    }

}
