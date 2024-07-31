package me.xpyex.plugin.xplib.bukkit.strings;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import java.io.File;
import me.xpyex.plugin.xplib.bukkit.XPLib;
import me.xpyex.plugin.xplib.bukkit.config.GsonUtil;
import me.xpyex.plugin.xplib.util.RootUtil;
import me.xpyex.plugin.xplib.util.files.FileUtil;
import me.xpyex.plugin.xplib.util.value.ValueUtil;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

public class NameUtil extends RootUtil {
    private static final JsonElement EMPTY_STR = new JsonPrimitive("");
    private static JsonObject ZH_CN;

    static {
        try {
            File f = new File(XPLib.getInstance().getDataFolder(), "minecraft/zh_cn.json");
            ZH_CN = GsonUtil.parseJsonObject(FileUtil.readFile(f));
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取Material对应的简体中文译名
     *
     * @param material 某个Material
     * @return material对应的中文译名
     */
    public static String getTranslationName(Material material) {
        if (material == null) return "";
        return ValueUtil.getOrDefault((() ->
                                           ((material.isBlock()) ?
                                                ZH_CN.get("block.minecraft." + material.toString().toLowerCase()) :
                                                ZH_CN.get("item.minecraft." + material.toString().toLowerCase())
                                           ).getAsString()
        ), material.toString().toLowerCase());
    }

    /**
     * 获取ItemStack的类型所对应的简体中文译名
     *
     * @param i 某个ItemStack
     * @return 其类型所对应的简体中文译名
     */
    public static String getTranslationName(ItemStack i) {
        return getTranslationName(i.getType());
        //
    }

    /**
     * 获取Block的类型所对应的简体中文译名
     *
     * @param b 某个Block
     * @return 其类型所对应的简体中文译名
     */
    public static String getTranslationName(Block b) {
        return getTranslationName(b.getType());
        //
    }

    /**
     * 获取该EntityType对应的简体中文译名
     *
     * @param type 实体类型
     * @return 对应的简体中文译名
     */
    public static String getTranslationName(EntityType type) {
        if (type == null) return "";

        return ValueUtil.getOrDefault(ZH_CN.get("entity.minecraft." + type.toString().toLowerCase()), EMPTY_STR).getAsString();
    }

    /**
     * 获取该Entity的类型所对应的简体中文译名
     *
     * @param entity 实体
     * @return 其类型所对应的简体中文译名
     */
    public static String getTranslationName(Entity entity) {
        return getTranslationName(entity.getType());
        //
    }
}
