package me.xpyex.plugin.xplib.bukkit.api;

import java.util.Arrays;
import java.util.List;

public class Tuple {
    private final List<Object> content;

    public Tuple(Object... objects) {
        content = Arrays.asList(objects);
        //
    }

    @SuppressWarnings("unchecked")
    public <T> T get(int i) {
        return (T) content.get(i);
        //
    }
}
