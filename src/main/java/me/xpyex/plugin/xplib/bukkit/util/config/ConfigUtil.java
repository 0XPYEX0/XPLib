package me.xpyex.plugin.xplib.bukkit.util.config;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
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
        ValueUtil.mustTrue("在 " + plugin.getName() + " 目录下已存在 " + path + " ，且非一个文件夹", !folder.isDirectory());

        for (File file : folder.listFiles()) {
            if (file.getName().endsWith(".json")) {
                JsonObject o = GsonUtil.parseJsonObject(FileUtil.readFile(file));
                result.add(o);
                CONFIGS.put(plugin.getName() + "/" + path + "/" + file.getName(), o);
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

    /**
     * 获取JsonObject下的某个键值 <br>
     * 若键值不存在，返回null
     * 若存在，返回包含值的JsonPrimitive
     *
     * @param obj JsonObject对象
     * @param key 键名. 例: 填入a.b，则获取名称为a的JsonObject下的b值
     * @return 值，但不一定有内容. JsonPrimitive必有内容
     */
    public static JsonPrimitive getValue(JsonObject obj, String key) {
        ValueUtil.notNull("需要获取的键名为空", key);
        String[] keys = key.split("\\.");

        if (keys.length >= 1) {
            JsonObject root = GsonUtil.copy(obj);
            for (int i = 0; i < keys.length; i++) {
                if (i == keys.length - 1) {  //最后一个值
                    return (JsonPrimitive) root.get(keys[i]);
                }

                root = root.get(keys[i]).getAsJsonObject();  //不是最后一个值，默认都为JsonObject; 若否，则抛出IllegalStateException
            }
        } else {
            return obj.get(key).getAsJsonPrimitive();
        }
        throw new IllegalStateException("未获取到键值: " + key);  //抛出IllegalStateException
    }

    public static String getStrValue(JsonObject obj, String key) {
        return getValue(obj, key).getAsString();
        //
    }


    public static int getIntValue(JsonObject obj, String key) {
        return getValue(obj, key).getAsInt();
        //
    }

    public static long getLongValue(JsonObject obj, String key) {
        return getValue(obj, key).getAsLong();
        //
    }

    public static boolean getBoolValue(JsonObject obj, String key) {
        return getValue(obj, key).getAsBoolean();
        //
    }

    public static float getFloatValue(JsonObject obj, String key) {
        return getValue(obj, key).getAsFloat();
        //
    }

    public static double getDoubleValue(JsonObject obj, String key) {
        return getValue(obj, key).getAsDouble();
        //
    }

    public static short getShortValue(JsonObject obj, String key) {
        return getValue(obj, key).getAsShort();
        //
    }

    public static byte getByteValue(JsonObject obj, String key) {
        return getValue(obj, key).getAsByte();
        //
    }

    public static BigInteger getBigIntValue(JsonObject obj, String key) {
        return getValue(obj, key).getAsBigInteger();
        //
    }

    public static BigDecimal getBigDecimalValue(JsonObject obj, String key) {
        return getValue(obj, key).getAsBigDecimal();
        //
    }

    public static Number getNumberValue(JsonObject obj, String key) {
        return getValue(obj, key).getAsNumber();
        //
    }

    public static JsonArray getJsonArrayValue(JsonObject obj, String key) {
        return getValue(obj, key).getAsJsonArray();
        //
    }

    public static JsonObject getJsonObjectValue(JsonObject obj, String key) {
        return getValue(obj, key).getAsJsonObject();
        //
    }
}
