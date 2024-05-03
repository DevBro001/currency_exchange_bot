package uz.pdp.lesson.bot.maneger;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import uz.pdp.lesson.bot.Bot;
import uz.pdp.lesson.bot.handlers.BaseHandler;
import uz.pdp.lesson.bot.handlers.CallBackQueryHandler;
import uz.pdp.lesson.bot.handlers.MessageHandler;

public class UpdateManager {

    private BaseHandler messageHandler ;
    private BaseHandler callBackQueryHandler ;

    public UpdateManager() {
        this.messageHandler = new MessageHandler();
        this.callBackQueryHandler = new CallBackQueryHandler();
    }

    public  void manage(Update update){
        if (update.message()!=null) {
            messageHandler.handle(update);
        }else if (update.callbackQuery()!=null){
            callBackQueryHandler.handle(update);
        }
    }
}
