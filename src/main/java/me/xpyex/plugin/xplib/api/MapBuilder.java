package me.xpyex.plugin.xplib.api;

import java.util.HashMap;
import java.util.Map;
import me.xpyex.plugin.xplib.util.value.ValueUtil;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class MapBuilder<K, V> {
    private final Map<K, V> map;

    private MapBuilder() {
        this.map = new HashMap<>();
        //
    }

    private MapBuilder(Class<K> c1, Class<V> c2) {
        this.map = new HashMap<>();
        //
    }

    private MapBuilder(Map<K, V> map) {
        this.map = map;
        //
    }

    @NotNull
    @Contract(value = " -> new", pure = true)
    public static MapBuilder<Object, Object> builder() {
        return new MapBuilder<>();
        //
    }

    @NotNull
    @Contract(value = "_, _ -> new", pure = true)
    public static <K, V> MapBuilder<K, V> builder(Class<K> keyType, Class<V> valueType) {
        return new MapBuilder<>(keyType, valueType);
        //
    }

    @NotNull
    @Contract(value = "_ -> new", pure = true)
    public static <K, V> MapBuilder<K, V> builder(Map<K, V> map) {
        return new MapBuilder<>(map);
        //
    }

    public MapBuilder<K, V> put(K key, V value) {
        this.map.put(key, value);
        return this;
    }

    public MapBuilder<K, V> remove(K key) {
        this.map.remove(key);
        return this;
    }

    public MapBuilder<K, V> putIfTrue(boolean condition, TryCallable<K> key, TryCallable<V> value) {
        if (condition)
            try {
                return put(key.call(), value.call());
            } catch (Throwable e) {
                throw new IllegalStateException(e);
            }
        return this;
    }

    public MapBuilder<K, V> removeIfTrue(boolean condition, TryCallable<K> key) {
        if (condition)
            try {
                return remove(key.call());
            } catch (Throwable e) {
                throw new IllegalStateException(e);
            }
        return this;
    }

    public Map<K, V> build() {
        return this.map;
        //
    }

    @SuppressWarnings("unchecked")
    public <M extends Map<K, V>> M build(Class<M> type) {
        return (M) build();
        //
    }

    /**
     * 当Map中存在指定key时，在lambda内返回value以执行，方便判空
     *
     * @param key 指定的key
     * @param v   执行的代码内容
     */
    public void doIfContainsKey(K key, TryConsumer<V> v) {
        ValueUtil.ifPresent(map.get(key), v1 -> {
            try {
                v.accept(v1);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        });
    }
}
