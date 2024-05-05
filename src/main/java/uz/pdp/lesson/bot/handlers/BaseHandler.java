package uz.pdp.lesson.bot.handlers;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.request.DeleteMessage;
import com.pengrad.telegrambot.request.SendMessage;
import uz.pdp.lesson.backend.model.MyUser;
import uz.pdp.lesson.backend.service.ArchiveService;
import uz.pdp.lesson.backend.service.UserService;
import uz.pdp.lesson.baen.BeanController;
import uz.pdp.lesson.bot.Bot;
import uz.pdp.lesson.bot.maker.MessageMaker;
import uz.pdp.lesson.bot.states.base.BaseState;
import uz.pdp.lesson.bot.states.child.MainState;

public abstract class BaseHandler {
    protected TelegramBot bot;
    protected MyUser curUser;
    protected Update update;
    protected UserService userService;
    protected ArchiveService archiveService;
    protected MessageMaker messageMaker;

    public BaseHandler() {
        this.bot = new TelegramBot(Bot.BOT_TOKEN);
        this.userService = BeanController.userServiceByThreadLocal.get();
        this.archiveService = BeanController.archiveServiceByThreadLocal.get();
        this.messageMaker = BeanController.messageMakerByThreadLocal.get();
    }

    public abstract void handle(Update update);


    protected MyUser getUserOrCreate(User from){
        MyUser myUser = userService.get(from.id());
        if (myUser==null){
            myUser = MyUser.builder()
                    .id(from.id())
                    .username(from.username())
                    .baseState(BaseState.MAIN_STATE.name())
                    .state(MainState.REGISTER.name())
                    .firstname(from.firstName())
                    .lastname(from.lastName())
                    .build();
            userService.save(myUser);
        }
        return myUser;
    }

    protected void deleteMessage(int messageId){
        DeleteMessage deleteMessage = new DeleteMessage(curUser.getId(), messageId);
        bot.execute(deleteMessage);
    }
}
