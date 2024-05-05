package uz.pdp.lesson.bot.handlers;

import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.*;
import uz.pdp.lesson.backend.model.Archive;
import uz.pdp.lesson.bot.states.base.BaseState;
import uz.pdp.lesson.bot.states.child.ArchiveState;
import uz.pdp.lesson.bot.states.child.MainState;

import java.util.Objects;

public class CallBackQueryHandler extends BaseHandler{


    @Override
    public void handle(Update update) {
        CallbackQuery callbackQuery = update.callbackQuery();
        User from = callbackQuery.from();
        super.curUser = getUserOrCreate(from);
        super.update = update;
        String baseStateString = curUser.getBaseState();
        BaseState baseState = BaseState.valueOf(baseStateString);
        if (Objects.equals(baseState,BaseState.MAIN_STATE)){
            mainSate();
        }else if (Objects.equals(baseState,BaseState.ARCHIVE_STATE)){
            // go to archive
            archiveState();
        }else if (Objects.equals(baseState,BaseState.CALCULATE_STATE)){
            // go to calculate
        }
    }

    private void archiveState() {
        String stateStr = curUser.getState();
        ArchiveState state = ArchiveState.valueOf(stateStr);
        CallbackQuery callbackQuery = update.callbackQuery();
        Message message = callbackQuery.message();
        String data = callbackQuery.data();
        switch (state){
            case CHOOSE_CURRENCY -> {
               
                if (data.equals("BACK")){
                    changeState(state);
                    SendMessage sendMessage = messageMaker.mainMenu(curUser);
                    bot.execute(sendMessage);
                    deleteMessage(message.messageId());
                    return;
                }
                Archive archive = Archive.builder()
                        .currency(data)
                        .userId(curUser.getId())
                        .build();
                archiveService.save(archive);
                SendMessage sendMessage = messageMaker.enterDateForArchive(curUser);
                bot.execute(sendMessage);
                curUser.setState(ArchiveState.ENTER_DATE.name());
                userService.save(curUser);
                deleteMessage(message.messageId());
            }
            case ENTER_DATE -> {
                if (data.equals("BACK")){
                    changeState(state);
                    SendMessage sendMessage = messageMaker.chooseCurrency(curUser);
                    bot.execute(sendMessage);
                    deleteMessage(message.messageId());
                    return;
                }
            }
            case RESULT -> {
                if (data.equals("BACK")){
                    changeState(state);
                    SendMessage sendMessage = messageMaker.enterDateForArchive(curUser);
                    bot.execute(sendMessage);
                    deleteMessage(message.messageId());
                    return;
                }else if (data.equals("BACK_TO_MAIN_MENU")){
                    curUser.setBaseState(BaseState.MAIN_STATE.name());
                    curUser.setState(MainState.MAIN_MENU.name());
                    userService.save(curUser);
                    SendMessage sendMessage = messageMaker.mainMenu(curUser);
                    bot.execute(sendMessage);
                }
            }
        }
    }

    private void mainSate() {
        String stateStr = curUser.getState();
        MainState state = MainState.valueOf(stateStr);
        CallbackQuery callbackQuery = update.callbackQuery();
        switch (state){
            case MAIN_MENU -> {
                String data = callbackQuery.data();
                mainMenu(data);


            }
        }
    }

    private void mainMenu(String data) {
        CallbackQuery callbackQuery = update.callbackQuery();
        Message message = callbackQuery.message();
        switch (data){
            case "ARCHIVE"->{
                chooseCurrency();
                deleteMessage(message.messageId());
            }
            case "CALCULATE"->{

            }
        }
    }

    private void chooseCurrency() {
        SendMessage sendMessage = messageMaker.chooseCurrency(curUser);
        bot.execute(sendMessage);
        curUser.setBaseState(BaseState.ARCHIVE_STATE.name());
        curUser.setState(ArchiveState.CHOOSE_CURRENCY.name());
        userService.save(curUser);
    }
    private void changeState(ArchiveState state) {
        ArchiveState pervState = state.getPervState();
        if (pervState==null) {
            curUser.setBaseState(BaseState.MAIN_STATE.name());
            curUser.setState(MainState.MAIN_MENU.name());
        }else {
            curUser.setState(pervState.name());
        }
        userService.save(curUser);
    }
}
