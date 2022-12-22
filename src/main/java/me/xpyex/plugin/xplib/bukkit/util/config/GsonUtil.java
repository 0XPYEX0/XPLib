package me.xpyex.plugin.xplib.bukkit.util.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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

    public static JsonObject copy(JsonObject o1) {
        try {
            return o1.deepCopy();
        } catch (IllegalAccessError ignored) {
            JsonObject result = new JsonObject();
            for (Map.Entry<String, JsonElement> entry : o1.entrySet()) {
                result.add(entry.getKey(), entry.getValue());
            }
            return result;
        }
    }

    /**
     * 获取JsonObject中的所有Key
     *
     * @param target 目标JsonObject
     * @return 所有Key组成的数组
     */
    public static Set<String> getKeysOfJsonObject(JsonObject target) {
        try {
            return target.keySet();
        } catch (NoSuchMethodError ignored) {
            Set<String> set = new HashSet<>();
            target.entrySet().forEach(E -> set.add(E.getKey()));
            return set;
        }
    }
}
