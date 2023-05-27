package me.xpyex.plugin.xplib.bukkit.util.reflect;

import me.xpyex.plugin.xplib.bukkit.util.RootUtil;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.Nullable;
import sun.reflect.Reflection;

public class ClassUtil extends RootUtil {
    @Nullable
    public static Class<?> getClass(String name, boolean needInitClass, boolean isDeepSearch) {
        try {
            return Class.forName(name, needInitClass, Reflection.getCallerClass().getClassLoader());
        } catch (ReflectiveOperationException ignored) {
            try {
                return Class.forName(name, needInitClass, ClassLoader.getSystemClassLoader());
            } catch (ReflectiveOperationException ignored1) {
                try {
                    return Class.forName(name, needInitClass, Bukkit.getServer().getClass().getClassLoader());
                } catch (ReflectiveOperationException ignored2) {
                    if (isDeepSearch) {
                        for (Thread thread : Thread.getAllStackTraces().keySet()) {
                            try {
                                return Class.forName(name, needInitClass, thread.getContextClassLoader());
                            } catch (ReflectiveOperationException ignored3) {
                            }
                        }
                    }
                    return null;
                }
            }
        }
    }
}
