package me.xpyex.plugin.xplib.api;

/**
 * 允许抛出任何内容的Runnable
 */
public interface TryRunnable {
    void run() throws Throwable;
}
