package me.xpyex.plugin.xplib.bukkit.api;

/**
 * 允许抛出任何错误的Callable
 */
public interface TryCallable<T> {
    T call() throws Throwable;
}
