package me.xpyex.plugin.xplib.bukkit.util.value;

import java.util.HashMap;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.Nullable;

public class ValueCacheUtil {
    private static final HashMap<String, HashMap<String, Object>> PLUGIN_VALUES = new HashMap<>();

    public static void addData(Plugin plugin, String key, Object value) {
        if (!PLUGIN_VALUES.containsKey(plugin.getName())) {
            PLUGIN_VALUES.put(plugin.getName(), new HashMap<>());
        }
        PLUGIN_VALUES.get(plugin.getName()).put(key, value);
    }

    public static void delData(Plugin plugin, String key) {
        if (PLUGIN_VALUES.containsKey(plugin.getName())) {
            PLUGIN_VALUES.get(plugin.getName()).remove(key);
        }
    }

    public static void delData(Plugin plugin) {
        PLUGIN_VALUES.remove(plugin.getName());
        //
    }

    public static void delList(Plugin plugin, String key) {
        if (PLUGIN_VALUES.containsKey(plugin.getName())) {
            for (String k : PLUGIN_VALUES.get(plugin.getName()).keySet()) {
                if (k.startsWith(key)) {
                    PLUGIN_VALUES.get(plugin.getName()).remove(k);
                }
            }
        }
    }

    public static boolean hasData(Plugin plugin, String key) {
        return PLUGIN_VALUES.containsKey(plugin.getName()) && PLUGIN_VALUES.get(plugin.getName()).containsKey(key);
        //
    }

    @Nullable
    @SuppressWarnings("unchecked")
    public static <T> T getData(Plugin plugin, String key) {
        if (hasData(plugin, key)) {
            return (T) PLUGIN_VALUES.get(plugin.getName()).get(key);
        }
        return null;
    }

    public static <T> T getData(Plugin plugin, String key, Class<T> returnType) {
        return getData(plugin, key);
        //
    }
}
