package me.xpyex.plugin.xplib.bukkit.util.bstats;

import java.util.HashMap;
import java.util.Map;
import me.xpyex.plugin.xplib.bukkit.XPLib;
import me.xpyex.plugin.xplib.bukkit.bstats.Metrics;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class BStatsUtil {
    public static Metrics hookWith(JavaPlugin plugin, int id) {
        return new Metrics(plugin, id);
        //
    }

    public static Metrics hookWith(Plugin plugin) {
        Metrics metrics = new Metrics(XPLib.getInstance(), 17099);
        metrics.addCustomChart(new Metrics.DrilldownPie("which_plugin", () -> {
            Map<String, Map<String, Integer>> map = new HashMap<>();
            Map<String, Integer> entry = new HashMap<>();
            entry.put(plugin.getName(), 1);
            map.put(plugin.getName(), entry);
            return map;
        }));
        return metrics;
    }
}
