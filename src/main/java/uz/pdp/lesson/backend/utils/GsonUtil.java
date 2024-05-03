package uz.pdp.lesson.backend.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public interface GsonUtil {
    Gson gson = buildGson();

    private static Gson buildGson() {
        return new GsonBuilder()
                .create();
    }

}
