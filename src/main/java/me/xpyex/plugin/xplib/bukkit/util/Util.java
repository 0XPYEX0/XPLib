package me.xpyex.plugin.xplib.bukkit.util;

import java.util.function.Function;

public class Util {
    public static <T> T getOrDefault(T value, T defaulted) {
        if (defaulted == null) {
            throw new IllegalArgumentException("Default参数不应为空");
        }
        return value != null ? value : defaulted;
    }

    public static <A, R> R getOrDefault(A argument, Function<A, R> function, R defaulted) {
        return getOrDefault(function.apply(argument), defaulted);
        //
    }
}
