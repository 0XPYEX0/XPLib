package me.xpyex.plugin.xplib.bukkit.api;

public interface TryFunction<T, R> {
    R apply(T t) throws Throwable;
}
