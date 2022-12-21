package me.xpyex.plugin.xplib.bukkit.util.version;

import me.xpyex.plugin.xplib.bukkit.XPLib;
import me.xpyex.plugin.xplib.bukkit.api.Version;
import org.bukkit.Bukkit;

public class VersionUtil {
    private static final int MAIN_VERSION = Integer.parseInt(Bukkit.getBukkitVersion().split("\\.")[1]);
    public static final Version VERSION = new Version(XPLib.getInstance().getDescription().getVersion());

    public static int getMainVersion() {
        return MAIN_VERSION;
        //
    }

    public static String getServerVersion() {
        return Bukkit.getVersion().split("-")[0];
        //
    }

    public static boolean requireXPLib(Version version) {
        return version.equals(VERSION);
        //
    }

    public static Version getXPLibVersion() {
        return VERSION;
        //
    }
}
