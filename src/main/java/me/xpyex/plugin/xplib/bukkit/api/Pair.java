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
