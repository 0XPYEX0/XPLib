package me.xpyex.plugin.xplib.bukkit.util.version;

import me.xpyex.plugin.xplib.bukkit.XPLib;
import me.xpyex.plugin.xplib.bukkit.api.Version;
import org.bukkit.Bukkit;

public class VersionUtil {
    public static final Version VERSION = new Version(XPLib.getInstance().getDescription().getVersion());
    private static final int MAIN_VERSION = new Version(Bukkit.getBukkitVersion()).getVersion(1);

    public static int getMainVersion() {
        return MAIN_VERSION;
        //
    }

    public static String getServerVersion() {
        return Bukkit.getVersion().split("-")[0];
        //
    }

    public static boolean requireXPLib(Version version) {
        return version.compareTo(VERSION) <= 0;
        //
    }

    public static Version getXPLibVersion() {
        return VERSION;
        //
    }
}
