package me.xpyex.plugin.xplib.bukkit.util.random;

import java.util.Collection;
import java.util.List;
import java.util.Random;
import me.xpyex.plugin.xplib.bukkit.util.RootUtil;
import org.jetbrains.annotations.Nullable;

public class RandomUtil extends RootUtil {
    @Nullable
    @SuppressWarnings("unchecked")
    public static <T> T getRandomValue(Collection<T> values) {
        if (values == null) return null;
        return (T) getRandomValue(values.toArray());
    }

    @Nullable
    public static <T> T getRandomValue(List<T> values) {
        if (values == null || values.size() == 0) return null;
        if (values.size() == 1) return values.get(0);
        return values.get(getRandomInt(values.size()));
    }

    @Nullable
    public static <T> T getRandomValue(T[] values) {
        if (values == null || values.length == 0) return null;
        if (values.length == 1) return values[0];
        return values[getRandomInt(values.length)];
    }

    public static int getRandomInt() {
        return new Random().nextInt();
        //
    }

    public static int getRandomInt(int bound) {
        return new Random().nextInt(bound);
        //
    }

    public static boolean getRandomBoolean() {
        return new Random().nextBoolean();
        //
    }

    public static float getRandomFloat() {
        return new Random().nextFloat();
        //
    }

    public static double getRandomDouble() {
        return new Random().nextDouble();
        //
    }

    public static long getRandomLong() {
        return new Random().nextLong();
        //
    }
}
