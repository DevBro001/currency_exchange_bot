package uz.pdp.lesson.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import uz.pdp.lesson.bot.maneger.UpdateManager;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Bot {
    public static final String BOT_TOKEN = "6891710529:AAFbxQPzHRHXEvRANOq2Bhev3jb63lywWB8";
    static final ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    static  ThreadLocal<UpdateManager> updateHandlerThreadLocal = ThreadLocal.withInitial(UpdateManager::new);
    public static void main(String[] args) {
        TelegramBot bot = new TelegramBot(BOT_TOKEN);
        bot.setUpdatesListener((updates) -> {
            for (Update update : updates) {
                CompletableFuture.runAsync( () -> {
                            try {                                updateHandlerThreadLocal.get().manage(update);
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                ,pool);
            }

            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        },Throwable::printStackTrace);
    }
}
