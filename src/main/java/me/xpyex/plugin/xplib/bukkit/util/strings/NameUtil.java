package me.xpyex.plugin.xplib.bukkit.util.strings;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import java.io.File;
import me.xpyex.plugin.xplib.bukkit.XPLib;
import me.xpyex.plugin.xplib.bukkit.util.Util;
import me.xpyex.plugin.xplib.bukkit.util.files.FileUtil;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

public class NameUtil {
    private static final Gson GSON = new GsonBuilder()
                                         .disableHtmlEscaping()
                                         .setPrettyPrinting()
                                         .create();
    private static final JsonElement EMPTY_STR = new JsonPrimitive("");
    private static JsonObject ZH_CN;
    static {
        try {
            File f = new File(XPLib.getInstance().getDataFolder(), "zh_cn.json");
            ZH_CN = GSON.fromJson(FileUtil.readFile(f), JsonObject.class);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public static String getTranslationName(Material material) {
        if (material == null) return "";

        if (material.isBlock()) return Util.getOrDefault(ZH_CN.get("block.minecraft." + material.toString().toLowerCase()), EMPTY_STR).getAsString();

        else if (material.isItem()) return Util.getOrDefault(ZH_CN.get("item.minecraft." + material.toString().toLowerCase()), EMPTY_STR).getAsString();

        else return material.toString().toLowerCase();
    }

    public static String getTranslationName(EntityType type) {
        if (type == null) return "";

        return Util.getOrDefault(ZH_CN.get("entity.minecraft." + type.toString().toLowerCase()), EMPTY_STR).getAsString();
    }

    public static String getTranslationName(Entity entity) {
        return getTranslationName(entity.getType());
        //
    }
}