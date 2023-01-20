package me.xpyex.plugin.xplib.bukkit.util.bstats;

import me.xpyex.plugin.xplib.bukkit.XPLib;
import me.xpyex.plugin.xplib.bukkit.bstats.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

public class BStatsUtil {
    /**
     * 插件与bStats挂钩
     * @param plugin 插件实例
     * @param id bStats ID
     * @return bStats统计实例
     */
    public static Metrics hookWith(JavaPlugin plugin, int id) {
        return new Metrics(plugin, id);
        //
    }

    /**
     * XPLib与bStats挂钩
     * @return bStats统计实例
     */
    public static Metrics hookWith() {
        return hookWith(XPLib.getInstance(), 17099);
    }
}
