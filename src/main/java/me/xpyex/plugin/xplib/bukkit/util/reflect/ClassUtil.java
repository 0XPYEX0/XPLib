package me.xpyex.plugin.xplib.bukkit.util.reflect;

import java.util.WeakHashMap;
import me.xpyex.plugin.xplib.bukkit.util.RootUtil;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;
import sun.reflect.Reflection;

public class ClassUtil extends RootUtil {
    private static final WeakHashMap<String, Class<?>> CACHE = new WeakHashMap<>();

    @NotNull
    public static Class<?> getClass(String name, boolean needInitClass, boolean isDeepSearch) throws ClassNotFoundException {
        if (!CACHE.containsKey(name)) {
            try {
                CACHE.put(name, Class.forName(name, needInitClass, Reflection.getCallerClass().getClassLoader()));
            } catch (ReflectiveOperationException ignored) {
                try {
                    CACHE.put(name, Class.forName(name, needInitClass, ClassLoader.getSystemClassLoader()));
                } catch (ReflectiveOperationException ignored1) {
                    try {
                        CACHE.put(name, Class.forName(name, needInitClass, Bukkit.getServer().getClass().getClassLoader()));
                    } catch (ReflectiveOperationException ignored2) {
                        if (isDeepSearch) {
                            for (Thread thread : Thread.getAllStackTraces().keySet()) {
                                try {
                                    CACHE.put(name, Class.forName(name, needInitClass, thread.getContextClassLoader()));
                                } catch (ReflectiveOperationException ignored3) {
                                }
                            }
                        }
                        throw new ClassNotFoundException();
                    }
                }
            }
        }
        return CACHE.get(name);
    }

    public static void clearCache() {
        CACHE.clear();
        //
    }
}
