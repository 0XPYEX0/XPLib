package me.xpyex.plugin.xplib.bukkit.util.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

public class GsonUtil {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    public static JsonObject parseJsonObject(String jsonText) {
        return GSON.fromJson(jsonText, JsonObject.class);
        //
    }

    public static String parseStr(Object json) {
        return GSON.toJson(json);
        //
    }
}
