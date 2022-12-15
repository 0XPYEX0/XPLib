package me.xpyex.plugin.xplib.bukkit.util;

public class Util {
    public static <T> T getOrDefault(T value, T defaulted) {
        return value != null ? value : defaulted;
        //
    }
}
