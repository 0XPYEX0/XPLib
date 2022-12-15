package me.xpyex.plugin.xplib.bukkit.util;

import org.bukkit.ChatColor;

public class MsgUtil {

    public static String getColorMsg(String msg) {
        if (msg == null || msg.trim().isEmpty()) return "";

        return ChatColor.translateAlternateColorCodes('&', msg);
    }
}
