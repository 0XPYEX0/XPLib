package me.xpyex.plugin.xplib.bukkit.util.bstats;

import me.xpyex.plugin.xplib.bukkit.XPLib;
import me.xpyex.plugin.xplib.bukkit.bstats.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

public class BStatsUtil {
    public static Metrics hookWith(JavaPlugin plugin, int id) {
        return new Metrics(plugin, id);
        //
    }

    public static Metrics hookWith() {
        return hookWith(XPLib.getInstance(), 17099);
    }
}
