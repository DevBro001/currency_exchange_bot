package uz.pdp.lesson.backend.service;

import uz.pdp.lesson.backend.currency.CurrencyInfo;
import uz.pdp.lesson.backend.utils.GsonUtil;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class CurrencyService {

    private String BASE_URL = "https://cbu.uz/uz/arkhiv-kursov-valyut/json/";
    private HttpClient client;

    public CurrencyService() {
        this.client = getHttpClient();
    }

    public CurrencyInfo[] getAllCurrenciesByNow() throws URISyntaxException, IOException, InterruptedException {
        HttpRequest request = getRequest(BASE_URL);
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode()==200) {
            String json = response.body();
            CurrencyInfo[] currencyInfos = GsonUtil.gson.fromJson(json, CurrencyInfo[].class);
            return currencyInfos;
        }else {
            throw new RuntimeException("Something wrong");
        }
    }

    public CurrencyInfo[] getCurrencyByName(String name) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest request = getRequest(BASE_URL+name+"/");
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode()==200) {
            String json = response.body();
            CurrencyInfo[] currencyInfos = GsonUtil.gson.fromJson(json, CurrencyInfo[].class);
            return currencyInfos;
        }else {
            throw new RuntimeException("Something wrong");
        }
    }


    public HttpRequest getRequest(String URL) throws URISyntaxException {
        return HttpRequest.newBuilder(new URI(URL)).GET().build();
    }
    public HttpClient getHttpClient(){
        return  HttpClient.newBuilder()
                .build();
    }
}
