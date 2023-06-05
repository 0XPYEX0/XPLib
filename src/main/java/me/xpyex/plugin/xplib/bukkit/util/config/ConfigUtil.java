package me.xpyex.plugin.xplib.bukkit.util.config;

import com.google.gson.JsonObject;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import me.xpyex.plugin.xplib.bukkit.util.RootUtil;
import me.xpyex.plugin.xplib.bukkit.util.files.FileUtil;
import me.xpyex.plugin.xplib.bukkit.util.value.ValueUtil;
import org.bukkit.plugin.Plugin;

public class ConfigUtil extends RootUtil {
    private static final HashMap<String, JsonObject> CONFIGS = new HashMap<>();

    /**
     * 读取并解析插件的config.json
     *
     * @param plugin 插件实例
     * @return config.json对应的JsonObject
     */
    public static JsonObject getConfig(Plugin plugin) {
        return getConfig(plugin, "config");
        //
    }

    /**
     * 读取并解析插件的config.json
     *
     * @param plugin 插件实例
     * @param type   解析到指定类的实例内，详情查阅Gson文档
     * @return 解析config.json到对应的实例
     */
    public static <T> T getConfig(Plugin plugin, Class<T> type) {
        return GsonUtil.parseJson(GsonUtil.parseStr(getConfig(plugin)), type);
        //
    }

    /**
     * 读取并解析插件文件夹下的配置文件
     *
     * @param plugin 插件实例
     * @param config 文件路径
     * @param type   解析到指定类的实例内，详情查阅Gson文档
     * @return 解析该文件后所对应的实例
     */
    public static <T> T getConfig(Plugin plugin, String config, Class<T> type) {
        return GsonUtil.parseJson(GsonUtil.parseStr(getConfig(plugin, config)), type);
        //
    }

    /**
     * 读取并解析插件文件夹下的配置文件
     *
     * @param plugin 插件实例
     * @param config 文件路径
     * @return 解析该文件后的JsonObject
     */
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

    /**
     * 获取插件目录下的所有json文件，并解析为JsonObject
     *
     * @param plugin 插件实例
     * @param path   需要获取的目录
     * @return JsonObject构成的List
     */
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
        ValueUtil.mustTrue(plugin.getName() + " 目录下没有 " + path + " 目录", folder.exists());
        ValueUtil.mustTrue("在 " + plugin.getName() + " 目录下已存在 " + path + " ，且非一个文件夹", folder.isDirectory());

        for (File file : folder.listFiles()) {
            if (file.getName().endsWith(".json")) {
                JsonObject o = GsonUtil.parseJsonObject(FileUtil.readFile(file));
                result.add(o);
                CONFIGS.put(plugin.getName() + "/" + path + "/" + file.getPath(), o);
            }
        }
        return result;
    }

    /**
     * 清除有关该插件的缓存，获取配置文件时都会重新加载
     *
     * @param plugin 插件实例
     */
    public static void reload(Plugin plugin) {
        HashMap<String, JsonObject> copied = new HashMap<>(CONFIGS);
        for (String s : copied.keySet()) {
            if (s.startsWith(plugin.getName() + "/")) {
                CONFIGS.remove(s);
            }
        }
    }

    /**
     * 清除所有缓存，获取配置文件时都会重新加载
     */
    public static void reload() {
        CONFIGS.clear();
        //
    }

    /**
     * 保存配置文件，并更新缓存
     *
     * @param plugin   插件实例
     * @param config   配置文件目录
     * @param content  配置文件内容
     * @param replaced 是否替换原有内容
     */
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
