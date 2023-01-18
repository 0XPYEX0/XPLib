package me.xpyex.plugin.xplib.bukkit.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.function.Function;

public class Util {
    public static <T> T getOrDefault(T value, T defaulted) {
        if (defaulted == null) {
            throw new IllegalArgumentException("Default参数不应为空");
        }
        return value != null ? value : defaulted;
    }

    public static <A, R> R getOrDefault(A argument, Function<A, R> function, R defaulted) {
        return getOrDefault(function.apply(argument), defaulted);
        //
    }

    public static boolean isNull(Callable<?>... callables) {
        if (callables.length == 0) {
            return true;
        }
        for (Callable<?> callable : callables) {
            try {
                if (callable.call() == null) {
                    return true;
                }
            } catch (Throwable ignored) {
                return true;
            }
        }
        return false;
    }

    public static boolean isEmpty(Callable<?>... callables) {
        if (callables.length == 0) {
            return true;
        }
        for (Callable<?> callable : callables) {
            try {
                if (isEmpty(callable.call())) {
                    return true;
                }
            } catch (Throwable ignored) {
                return true;
            }
        }
        return false;
    }

    public static boolean isNull(Object... objects) {
        if (objects.length == 0) {
            return true;
        }
        for (Object o : objects) {
            if (o == null) {
                return true;
            }
        }
        return false;
    }

    public static boolean isEmpty(Object... objects) {
        if (objects.length == 0) {
            return true;
        }
        for (Object o : objects) {
            if (o == null) {
                return true;
            }
            if (o instanceof String) {
                if (((String) o).isEmpty()) return true;
            }
            if (o instanceof Map) {
                if (((Map<?, ?>) o).isEmpty()) return true;
            }
            if (o instanceof Collection) {
                if (((Collection<?>) o).isEmpty()) return true;
            }
            if (o instanceof Iterable) {
                if (!((Iterable<?>) o).iterator().hasNext()) return true;
            }
            if (o instanceof Iterator) {
                if (!((Iterator<?>) o).hasNext()) return true;
            }
        }
        return false;
    }
}
