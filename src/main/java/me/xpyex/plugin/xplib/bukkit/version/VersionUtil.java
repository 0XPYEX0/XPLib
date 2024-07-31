package me.xpyex.plugin.xplib.bukkit.version;

import me.xpyex.plugin.xplib.api.Version;
import me.xpyex.plugin.xplib.bukkit.XPLib;
import me.xpyex.plugin.xplib.util.RootUtil;
import org.bukkit.Bukkit;

public class VersionUtil extends RootUtil {
    private static final Version XPLIB_VER = new Version(XPLib.getInstance().getDescription().getVersion());
    private static final int MAIN_VERSION = new Version(Bukkit.getBukkitVersion()).getVersion(1);

    public static int getMainVersion() {
        return MAIN_VERSION;
        //
    }

    public static String getServerVersion() {
        return Bukkit.getVersion().split("-")[0];
        //
    }

    @Deprecated
    public static boolean requireXPLib(Version version) {
        return isUpperXPLib(version);
        //
    }

    /**
     * 检查XPLib的版本是否比要求版本更高
     *
     * @param version 目标版本
     * @return 若XPLib版本高于或等于version，返回true；反之返回false
     */
    public static boolean isUpperXPLib(Version version) {
        return XPLIB_VER.compareTo(version) >= 0;
        //
    }

    public static Version getXPLibVersion() {
        return XPLIB_VER;
        //
    }
}
