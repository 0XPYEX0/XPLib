package me.xpyex.plugin.xplib.bukkit.util.config;

import com.google.gson.JsonObject;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import me.xpyex.plugin.xplib.bukkit.util.files.FileUtil;
import org.bukkit.plugin.Plugin;

public class ConfigUtil {
    private static final HashMap<String, JsonObject> CONFIGS = new HashMap<>();

    public static JsonObject getConfig(Plugin plugin) {
        return getConfig(plugin, "config");
        //
    }

    public static JsonObject getConfig(Plugin plugin, String config) {
        if (!CONFIGS.containsKey(plugin.getName() + "/" + config)) {
            try {
                CONFIGS.put(plugin.getName() + "/" + config, GsonUtil.parseJsonObject(FileUtil.readFile(new File(plugin.getDataFolder(), config + ".json"))));
            } catch (Throwable e) {
                new IllegalStateException(e).printStackTrace();
            }
        }
        return CONFIGS.get(plugin.getName() + "/" + config);
    }

    public static List<JsonObject> getConfigs(Plugin plugin, String path) throws Throwable {
        ArrayList<JsonObject> result = new ArrayList<>();
        for (String key : CONFIGS.keySet()) {
            if (key.startsWith(plugin.getName() + "/" + path)) {
                result.add(CONFIGS.get(key));
            }
        }
        if (result.size() != 0) {
            return result;
        }
        File folder = new File(plugin.getDataFolder(), path);
        if (folder.exists()) {
            if (!folder.isDirectory()) {
                throw new IllegalStateException("在 " + plugin.getName() + " 目录下已存在 " + path + " ，且非一个文件夹");
            }
        } else {
            throw new IllegalStateException(plugin.getName() + " 目录下没有 " + path + " 目录");
        }
        for (File file : folder.listFiles()) {
            if (file.getName().endsWith(".json")) {
                JsonObject o = GsonUtil.parseJsonObject(FileUtil.readFile(file));
                result.add(o);
                CONFIGS.put(plugin.getName() + "/" + path + "/" + file.getPath(), o);
            }
        }
        return result;
    }

    public static void reload(Plugin plugin) {
        HashMap<String, JsonObject> copied = new HashMap<>(CONFIGS);
        for (String s : copied.keySet()) {
            if (s.startsWith(plugin.getName() + "/")) {
                CONFIGS.remove(s);
            }
        }
    }

    public static void reload() {
        CONFIGS.clear();
        //
    }

    public static void saveConfig(Plugin plugin, String config, String content, boolean replaced) {
        File f = new File(plugin.getDataFolder(), config + ".json");
        if (f.exists() && !replaced) return;
        try {
            FileUtil.writeFile(f, content);
            CONFIGS.put(plugin.getName() + "/" + config, GsonUtil.parseJsonObject(content));
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
