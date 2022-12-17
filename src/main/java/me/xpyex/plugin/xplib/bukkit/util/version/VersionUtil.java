package me.xpyex.plugin.xplib.bukkit.util.version;

import org.bukkit.Bukkit;

public class VersionUtil {
    private static final int MAIN_VERSION = Integer.parseInt(Bukkit.getBukkitVersion().split("\\.")[1]);

    public static int getMainVersion() {
        return MAIN_VERSION;
        //
    }

    public static String getServerVersion() {
        return Bukkit.getVersion().split("-")[0];
    }
}
