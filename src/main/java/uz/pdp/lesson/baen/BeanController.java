package uz.pdp.lesson.baen;

import uz.pdp.lesson.backend.service.ArchiveService;
import uz.pdp.lesson.backend.service.CurrencyService;
import uz.pdp.lesson.backend.service.UserService;
import uz.pdp.lesson.bot.maker.MessageMaker;

public interface BeanController {
    ThreadLocal<UserService> userServiceByThreadLocal = ThreadLocal.withInitial(UserService::new);
    ThreadLocal<CurrencyService> currencyServiceByThreadLocal = ThreadLocal.withInitial(CurrencyService::new);
    ThreadLocal<ArchiveService> archiveServiceByThreadLocal = ThreadLocal.withInitial(ArchiveService::new);
    ThreadLocal<MessageMaker> messageMakerByThreadLocal = ThreadLocal.withInitial(MessageMaker::new);
}
