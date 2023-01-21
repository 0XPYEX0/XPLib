package me.xpyex.plugin.xplib.bukkit.util;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.Callable;
import org.jetbrains.annotations.NotNull;

public class Util {
    /**
     * 安全获取值，类似Optional
     *
     * @param value     需要判定的值.
     * @param defaulted 若value为null，则返回此实例
     * @return 返回安全的值
     */
    @NotNull
    public static <T> T getOrDefault(T value, T defaulted) {
        if (defaulted == null) {
            throw new IllegalArgumentException("Default参数不应为空");
        }
        return value != null ? value : defaulted;
    }

    /**
     * 安全获取值，类似Optional
     *
     * @param callable  执行返回值的方法.
     * @param defaulted 如callable的返回值为null，或过程中出现错误，则返回此
     * @param errMsg    描述错误的信息
     * @return 返回安全的值，不会出现空指针
     */
    @NotNull
    public static <T> T getOrDefault(Callable<T> callable, T defaulted, String errMsg) {
        if (defaulted == null) {
            throw new IllegalArgumentException("Default参数不应为空");
        }
        try {
            return getOrDefault(callable.call(), defaulted);
        } catch (Throwable e) {
            new Throwable(errMsg, e).printStackTrace();
            return defaulted;
        }
    }

    /**
     * 安全获取值，类似Optional
     *
     * @param callable  执行返回值的方法.
     * @param defaulted 如callable的返回值为null，或过程中出现错误，则返回此
     * @return 返回安全的值，不会出现空指针
     */
    @NotNull
    public static <T> T getOrDefault(Callable<T> callable, T defaulted) {
        return getOrDefault(callable, defaulted, "在执行XPLib的 Util.getOrDefault(Callable, Object) 方法时，callable过程出现错误: ");
        //
    }

    /**
     * 检查Callable的返回值是否存在null，是安全的方法
     *
     * @param callables 某些会返回实例的方法体
     * @return 其中是否出现null
     */
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

    /**
     * 检查Callable的返回值是否存在空，是安全的方法
     *
     * @param callables 某些会返回实例的方法体
     * @return 其中是否出现空
     */
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

    /**
     * 检查传入的值是否存在null
     *
     * @param objects 要检查的实例
     * @return 是否存在null
     */
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

    /**
     * 检查传入的值是否存在null
     *
     * @param msg     若存在null，抛出IllegalArgumentException，此为描述信息
     * @param objects 要检查的实例
     */
    public static void checkNull(String msg, Object... objects) {
        if (isNull(objects))
            throw new IllegalArgumentException(msg);
    }

    /**
     * 检查传入的值是否存在空
     *
     * @param objects 要检查的实例
     * @return 是否存在空
     */
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
            try {
                if (Array.getLength(o) == 0) return true;  //如果是Object[]，且没有内容
            } catch (IllegalArgumentException ignored) {
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

    /**
     * 若条件为false，则抛出IllegalStateException
     *
     * @param result 条件
     * @param errMsg 错误信息
     */
    public static void checkTrue(boolean result, String errMsg) {
        if (!result)
            throw new IllegalStateException(errMsg);
    }
}
