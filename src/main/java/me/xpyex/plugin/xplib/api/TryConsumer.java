package me.xpyex.plugin.xplib.api;

public interface TryConsumer<T> {
    void accept(T t) throws Throwable;
}
