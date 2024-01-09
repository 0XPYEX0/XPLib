package me.xpyex.plugin.xplib.bukkit.api;

public interface TryConsumer<T> {
    void accept(T t) throws Throwable;
}
