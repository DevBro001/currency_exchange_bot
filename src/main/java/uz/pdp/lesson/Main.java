package uz.pdp.lesson;

import uz.pdp.lesson.backend.jsonModels.currency.CurrencyInfo;
import uz.pdp.lesson.backend.service.CurrencyService;

import java.io.IOException;
import java.net.URISyntaxException;

public class Main {
    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
        CurrencyService currencyService = new CurrencyService();
        CurrencyInfo[] allCurrenciesByNow = currencyService.getCurrencyByName("USD");
        for (CurrencyInfo currencyInfo : allCurrenciesByNow) {
            System.out.printf("""
                    Currency: %s
                    Nominal: %s
                    %n""", currencyInfo.getCode(),currencyInfo.getRate());
            System.out.println("================================");
        }
        System.out.println("Hello world!");
    }
}