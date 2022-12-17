package me.xpyex.plugin.xplib.bukkit.util.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class GsonUtil {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    public static JsonObject parseJsonObject(String jsonText) {
        return parseJson(jsonText, JsonObject.class);
        //
    }

    public static JsonArray parseJsonArray(String jsonText) {
        return parseJson(jsonText, JsonArray.class);
        //
    }

    public static <T> T parseJson(String jsonText, Class<T> type) {
        return GSON.fromJson(jsonText, type);
        //
    }

    public static String parseStr(Object json) {
        return GSON.toJson(json);
        //
    }
}
