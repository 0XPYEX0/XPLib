package me.xpyex.plugin.xplib.bukkit.util.reflect;

import java.lang.reflect.Field;
import me.xpyex.plugin.xplib.bukkit.util.RootUtil;
import org.jetbrains.annotations.Nullable;

public class FieldUtil extends RootUtil {
    @Nullable
    public static Field getClassField(Class<?> clazz, String key) {
        try {
            return clazz.getField(key);
        } catch (ReflectiveOperationException ignored) {
            try {
                return clazz.getDeclaredField(key);
            } catch (ReflectiveOperationException ignored1) {
                if (clazz.getSuperclass() != null) {
                    return getClassField(clazz.getSuperclass(), key);
                }
                return null;
            }
        }
    }

    @Nullable
    @SuppressWarnings("unchecked")
    public static <T> T getObjectField(Object obj, String key) {
        Field field = getClassField(obj.getClass(), key);
        if (field == null) return null;
        try {
            boolean accessible = field.isAccessible();
            field.setAccessible(true);
            Object result = field.get(obj);
            field.setAccessible(accessible);
            return (T) result;
        } catch (ReflectiveOperationException ignored) {
            return null;
        }
    }
}
