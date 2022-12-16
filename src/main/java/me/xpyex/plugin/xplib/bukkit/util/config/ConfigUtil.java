package me.xpyex.plugin.xplib.bukkit.util.config;

import com.google.gson.JsonObject;
import java.io.File;
import java.util.HashMap;
import me.xpyex.plugin.xplib.bukkit.util.files.FileUtil;
import org.bukkit.plugin.Plugin;

public class ConfigUtil {
    private static final HashMap<String, JsonObject> CONFIGS = new HashMap<>();

    public static JsonObject getConfig(Plugin plugin) {
        return getConfig(plugin, "config");
        //
    }

    public static JsonObject getConfig(Plugin plugin, String config) {
        if (!CONFIGS.containsKey(plugin.getName() + "_" + config)) {
            try {
                CONFIGS.put(plugin.getName() + "_" + config, GsonUtil.parseJsonObject(FileUtil.readFile(new File(plugin.getDataFolder(), config + ".json"))));
            } catch (Throwable e) {
                throw new IllegalStateException(e);
            }
        }
        return CONFIGS.get(plugin.getName() + "_" + config);
    }

    public static void reload(Plugin plugin) {
        for (String s : CONFIGS.keySet()) {
            if (s.startsWith(plugin.getName() + "_")) {
                CONFIGS.remove(s);
            }
        }
    }

    public static void saveConfig(Plugin plugin, String config, String content, boolean replaced) {
        File f = new File(plugin.getDataFolder(), config + ".json");
        if (f.exists() && !replaced) return;
        try {
            FileUtil.writeFile(f, content);
            CONFIGS.put(plugin.getName() + "_" + config, GsonUtil.parseJsonObject(content));
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
