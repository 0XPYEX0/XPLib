package me.xpyex.plugin.xplib.api;

public interface TryFunction<T, R> {
    R apply(T t) throws Throwable;
}
