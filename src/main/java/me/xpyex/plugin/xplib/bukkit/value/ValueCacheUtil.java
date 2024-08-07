package me.xpyex.plugin.xplib.bukkit.value;

import java.util.HashMap;
import java.util.Optional;
import me.xpyex.plugin.xplib.util.RootUtil;
import me.xpyex.plugin.xplib.util.value.ValueUtil;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class ValueCacheUtil extends RootUtil {
    private static final HashMap<String, HashMap<String, Object>> PLUGIN_VALUES = new HashMap<>();

    public static void setData(Plugin plugin, String key, Object value) {
        if (ValueUtil.isEmpty(plugin, key, value)) return;

        PLUGIN_VALUES.computeIfAbsent(plugin.getName(), map -> new HashMap<>()).put(key, value);
    }

    public static void setData(String key, MetadataValue value) {
        ValueUtil.notNull("This MetadataValue does not contain a plugin!", key, value, value.getOwningPlugin(), value.value());
        setData(value.getOwningPlugin(), key, value.value());
    }

    public static void delData(Plugin plugin, String key) {
        if (ValueUtil.isEmpty(plugin, key)) return;

        ValueUtil.ifPresent(PLUGIN_VALUES.get(plugin.getName()), map -> map.remove(key));
    }

    public static void delData(Plugin plugin) {
        if (plugin == null) return;

        PLUGIN_VALUES.remove(plugin.getName());
    }

    public static void delList(Plugin plugin, String key) {
        if (ValueUtil.isEmpty(plugin, key)) return;

        if (PLUGIN_VALUES.containsKey(plugin.getName())) {
            for (String k : PLUGIN_VALUES.get(plugin.getName()).keySet()) {
                if (k.startsWith(key)) {
                    PLUGIN_VALUES.get(plugin.getName()).remove(k);
                }
            }
        }
    }

    public static boolean hasData(Plugin plugin, String key) {
        if (ValueUtil.isEmpty(plugin, key)) return false;

        return PLUGIN_VALUES.containsKey(plugin.getName()) && PLUGIN_VALUES.get(plugin.getName()).containsKey(key);
    }

    @NotNull
    @SuppressWarnings("unchecked")
    public static <T> Optional<T> getData(Plugin plugin, String key) {
        return Optional.ofNullable((T) PLUGIN_VALUES.get(plugin.getName()).get(key));
        //
    }

    @NotNull
    public static <T> Optional<T> getData(Plugin plugin, String key, Class<T> returnType) {
        return getData(plugin, key);
        //
    }
}
