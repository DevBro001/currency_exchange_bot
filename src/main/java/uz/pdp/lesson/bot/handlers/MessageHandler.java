package uz.pdp.lesson.bot.handlers;

import com.pengrad.telegrambot.model.*;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendDocument;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import uz.pdp.lesson.backend.model.Archive;
import uz.pdp.lesson.backend.model.MyUser;
import uz.pdp.lesson.bot.states.base.BaseState;
import uz.pdp.lesson.bot.states.child.ArchiveState;
import uz.pdp.lesson.bot.states.child.MainState;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class MessageHandler extends BaseHandler{

    @Override
    public void handle(Update update) {
        Message message = update.message();
        User from = message.from();
        super.update = update;
        super.curUser = getUserOrCreate(from);
        String text = message.text();


        if (Objects.equals(text,"/start")){
            if(Objects.isNull( curUser.getPhoneNumber()) ||  curUser.getPhoneNumber().isEmpty()){
                enterPhoneNumber();
            }else {
                mainManu();
            }
        }else {
            String baseStateString = curUser.getBaseState();
            BaseState baseState = BaseState.valueOf(baseStateString);
            if (Objects.equals(baseState,BaseState.MAIN_STATE)){
                mainState();
            }else if (Objects.equals(baseState,BaseState.ARCHIVE_STATE)){
                // go to archive
                archiveSate();
            }else if (Objects.equals(baseState,BaseState.CALCULATE_STATE)){
                // go to calculate
                calculate();
            }
        }
    }


    private void calculate() {
    }

    private void archiveSate() {
        String stateStr = curUser.getState();
        ArchiveState state = ArchiveState.valueOf(stateStr);
        switch (state){
            case ENTER_DATE -> {
                Message message = update.message();
                String text = message.text();
                if (text==null){
                    incorrectData("Date");
                    SendMessage sendMessage = messageMaker.enterDateForArchive(curUser);
                    bot.execute(sendMessage);
                    return;
                }

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    Date date = simpleDateFormat.parse(text);
                    Archive archive = archiveService.get(curUser.getId());
                    archive.setDate(date);
                    archiveService.save(archive);
                } catch (ParseException e) {
                    incorrectData("Date");
                    SendMessage sendMessage = messageMaker.enterDateForArchive(curUser);
                    bot.execute(sendMessage);
                    return;
                }
                Archive result = archiveService.getResult(curUser.getId());
                SendMessage shownResultForArchive = messageMaker.showResultForArchive(curUser, result);
                bot.execute(shownResultForArchive);
                curUser.setState(ArchiveState.RESULT.name());
                userService.save(curUser);
            }

        }

    }

    private void mainState() {
        String stateStr = curUser.getState();
        MainState state = MainState.valueOf(stateStr);
        switch (state){
            case REGISTER -> {
                Message message = update.message();
                Contact contact = message.contact();
                if (contact!=null){
                    String phoneNumber = contact.phoneNumber();
                    curUser.setPhoneNumber(phoneNumber);
                    userService.save(curUser);
                    mainManu();
                }else {
                    incorrectData("Phone Number");
                }

            }

        }
    }

    private void incorrectData(String data  ) {
        bot.execute(new SendMessage(curUser.getId(),"You entered incorrect " + data));

    }

    private void mainManu() {
        SendMessage sendMessage = messageMaker.mainMenu(curUser);
        bot.execute(sendMessage);
        curUser.setState(MainState.MAIN_MENU.name());
        userService.save(curUser);
    }

    public void enterPhoneNumber(){
        SendMessage sendMessage = messageMaker.enterPhoneNumber(curUser);
        bot.execute(sendMessage);
    }
}
