package me.xpyex.plugin.xplib.bukkit.util.config;

import com.google.gson.JsonObject;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import me.xpyex.plugin.xplib.bukkit.util.RootUtil;
import me.xpyex.plugin.xplib.bukkit.util.files.FileUtil;
import me.xpyex.plugin.xplib.bukkit.util.value.ValueUtil;
import org.bukkit.plugin.Plugin;

public class ConfigUtil extends RootUtil {
    private static final HashMap<String, Object> CONFIGS = new HashMap<>();

    @Deprecated
    public static JsonObject getConfig(Plugin plugin) {
        return getConfig(plugin, JsonObject.class);
        //
    }

    @Deprecated
    public static JsonObject getConfig(Plugin plugin, String path) {
        return getConfig(plugin, path, JsonObject.class);
        //
    }

    public static <T> T getConfig(Plugin plugin, Class<T> type) {
        return getConfig(plugin, "config", type);
        //
    }

    @SuppressWarnings("unchecked")
    public static <T> T getConfig(Plugin plugin, String path, Class<T> type) {
        String key = plugin.getName() + "/" + path;
        if (!CONFIGS.containsKey(key)) {
            try {
                CONFIGS.put(key, GsonUtil.parseJson(FileUtil.readFile(new File(plugin.getDataFolder(), path + ".json")), type));
            } catch (IOException e) {
                new IllegalStateException("配置文件访问异常", e).printStackTrace();
                return null;
            }
        }
        return (T) CONFIGS.get(key);
    }

    public static void reload() {
        CONFIGS.clear();
        //
    }

    /**
     * 清除有关该插件的缓存，获取配置文件时都会重新加载
     *
     * @param plugin 插件实例
     */
    public static void reload(Plugin plugin) {
        HashMap<String, Object> copied = new HashMap<>(CONFIGS);
        for (String s : copied.keySet()) {
            if (s.startsWith(plugin.getName() + "/")) {
                CONFIGS.remove(s);
            }
        }
    }

    public static void saveConfig(Plugin plugin, String path, Object obj, boolean replaced) {
        File file = new File(plugin.getDataFolder(), path + ".json");
        if (!replaced && file.exists()) return;
        try {
            FileUtil.writeFile(file, GsonUtil.parseStr(obj));
            CONFIGS.put(plugin.getName() + "/" + path, obj);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public static void saveConfig(Plugin plugin, String path, boolean replaced) {
        ValueUtil.ifPresent(CONFIGS.get(plugin.getName() + "/" + path), config -> saveConfig(plugin, path, config, replaced));
    }
}
