package me.xpyex.plugin.xplib.bukkit.api;

public class Pair<K, V> {
    private final K key;
    private final V value;

    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
        //
    }

    public V getValue() {
        return value;
        //
    }

    //泛型拿到的Pair无法指定内部类型，可用此方法
    @SuppressWarnings("unchecked")
    public <T> T getKey(Class<T> returnType) {
        return (T) key;
    }

    //泛型拿到的Pair无法指定内部类型，可用此方法
    @SuppressWarnings("unchecked")
    public <T> T getValue(Class<T> returnType) {
        return (T) value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof Pair<?, ?>) {
            Pair<?, ?> pair = (Pair<?, ?>) o;
            return this.getKey().equals(pair.getKey()) && this.getValue().equals(pair.getValue());
        }
        return false;
    }
}
