package me.xpyex.plugin.xplib.bukkit.api;

public class Tuple {
    private final Object[] content;

    public Tuple(Object... objects) {
        content = objects;
        //
    }

    @SuppressWarnings("unchecked")
    public <T> T get(int i) {
        return (T) content[i];
        //
    }

    public <T> T get(int i, @SuppressWarnings("unused") Class<T> type) {
        return get(i);
        //
    }
}
