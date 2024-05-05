package uz.pdp.lesson.backend.service;

import uz.pdp.lesson.backend.jsonModels.currency.CurrencyInfo;
import uz.pdp.lesson.backend.model.Archive;
import uz.pdp.lesson.backend.model.MyUser;
import uz.pdp.lesson.backend.repository.FileWriterAndLoader;
import uz.pdp.lesson.backend.statics.PathConstants;
import uz.pdp.lesson.baen.BeanController;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class ArchiveService implements BaseService, PathConstants {

    private FileWriterAndLoader<Archive> writerAndLoader;
    private CurrencyService currencyService;

    public ArchiveService() {
        this.writerAndLoader = new FileWriterAndLoader<>(ARCHIVES_PATH);
        this.currencyService = BeanController.currencyServiceByThreadLocal.get();
    }

    public void save(Archive archive){
        List<Archive> archives = writerAndLoader.load(Archive.class);
        for (int i = 0; i < archives.size(); i++) {
            Archive cur = archives.get(i);
            if (Objects.equals(cur.getUserId(),archive.getUserId())){
                archives.set(i,archive);
                writerAndLoader.write(archives);
                return;
            }
        }
        archives.add(archive);
        writerAndLoader.write(archives);
        return;
    }
    public Archive get(Long id){
        List<Archive> archives = writerAndLoader.load(Archive.class);
        for (int i = 0; i < archives.size(); i++) {
            Archive cur = archives.get(i);
            if (Objects.equals(cur.getUserId(),id)){
                return cur;
            }
        }
        return null;
    }
    public Archive getResult(Long id){
        Archive archive = get(id);
        Date date = archive.getDate();
        String currency = archive.getCurrency();
        if (date!=null&&Objects.nonNull(currency)&&!currency.isEmpty()){
            try {
                CurrencyInfo[] currencyByNameAndDate = currencyService.getCurrencyByNameAndDate(currency, date);
                CurrencyInfo currencyInfo = currencyByNameAndDate[0];
                String rate = currencyInfo.getRate();
                archive.setRate(rate);
                save(archive);
                return archive;
            } catch (Exception e) {
                return null;
            }
        }else {
            return null;
        }
    }
}
