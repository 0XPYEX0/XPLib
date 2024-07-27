package me.xpyex.plugin.xplib.util.random;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import me.xpyex.plugin.xplib.util.RootUtil;
import org.jetbrains.annotations.Nullable;

public class RandomUtil extends RootUtil {
    private static final ThreadLocalRandom RANDOM = ThreadLocalRandom.current();

    @Nullable
    @SuppressWarnings("unchecked")
    public static <T> T getRandomValue(Collection<T> values) {
        if (values == null || values.isEmpty()) return null;
        if (values instanceof List) {
            List<T> list = (List<T>) values;
            if (list.size() == 1) return list.get(0);
            return list.get(getRandomInt(values.size()));
        }
        return (T) getRandomValue(values.toArray());
    }

    @Nullable
    public static <T> T getRandomValue(T[] values) {
        if (values == null || values.length == 0) return null;
        if (values.length == 1) return values[0];
        return values[getRandomInt(values.length)];
    }

    public static int getRandomInt() {
        return RANDOM.nextInt();
        //
    }

    public static int getRandomInt(int bound) {
        return RANDOM.nextInt(bound);
        //
    }

    public static boolean getRandomBoolean() {
        return RANDOM.nextBoolean();
        //
    }

    public static float getRandomFloat() {
        return RANDOM.nextFloat();
        //
    }

    public static double getRandomDouble() {
        return RANDOM.nextDouble();
        //
    }

    public static long getRandomLong() {
        return RANDOM.nextLong();
        //
    }
}
