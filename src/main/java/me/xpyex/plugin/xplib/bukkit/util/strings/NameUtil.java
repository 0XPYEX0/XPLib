package me.xpyex.plugin.xplib.bukkit.util.strings;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import java.io.File;
import me.xpyex.plugin.xplib.bukkit.XPLib;
import me.xpyex.plugin.xplib.bukkit.util.Util;
import me.xpyex.plugin.xplib.bukkit.util.config.GsonUtil;
import me.xpyex.plugin.xplib.bukkit.util.files.FileUtil;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

public class NameUtil {
    private static final JsonElement EMPTY_STR = new JsonPrimitive("");
    private static JsonObject ZH_CN;
    static {
        try {
            File f = new File(XPLib.getInstance().getDataFolder(), "zh_cn.json");
            ZH_CN = GsonUtil.parseJsonObject(FileUtil.readFile(f));
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public static String getTranslationName(Material material) {
        if (material == null) return "";
        return Util.getOrDefault(material, (m) -> {
            return ((m.isBlock()) ? ZH_CN.get("block.minecraft." + m.toString().toLowerCase()) :
             ZH_CN.get("item.minecraft." + m.toString().toLowerCase())).getAsString();
        }, material.toString().toLowerCase());
    }

    public static String getTranslationName(ItemStack i) {
        return getTranslationName(i.getType());
        //
    }

    public static String getTranslationName(Block b) {
        return getTranslationName(b.getType());
        //
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
