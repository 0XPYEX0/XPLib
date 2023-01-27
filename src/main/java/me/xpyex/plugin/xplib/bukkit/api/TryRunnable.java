package me.xpyex.plugin.xplib.bukkit.api;

/**
 * 允许抛出任何内容的Runnable
 */
public interface TryRunnable {
    void run() throws Throwable;
}
