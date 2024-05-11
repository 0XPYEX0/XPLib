package me.xpyex.plugin.xplib.bukkit.util.reflect;

import java.lang.reflect.Field;
import java.util.WeakHashMap;
import me.xpyex.plugin.xplib.bukkit.util.RootUtil;
import me.xpyex.plugin.xplib.bukkit.util.value.ValueUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FieldUtil extends RootUtil {
    private static final WeakHashMap<String, Field> FIELD_CACHE = new WeakHashMap<>();

    @NotNull
    public static Field getClassField(Class<?> clazz, String key) throws ReflectiveOperationException {
        ValueUtil.notEmpty("参数不应为空值", clazz, key);
        String mapKey = clazz.getName() + key;
        if (FIELD_CACHE.containsKey(mapKey)) {
            return FIELD_CACHE.get(mapKey);
        }
        try {
            FIELD_CACHE.put(mapKey, clazz.getDeclaredField(key));
            return FIELD_CACHE.get(mapKey);
        } catch (ReflectiveOperationException ignored) {
            if (clazz.getSuperclass() != null) {
                return getClassField(clazz.getSuperclass(), key);
            }
        }
        throw new NoSuchFieldException("类 " + clazz.getName() + " 内没有字段 " + key);
    }

    @Nullable
    @SuppressWarnings("unchecked")
    public static <T> T getObjectField(Object obj, String key) throws ReflectiveOperationException {
        Field field = getClassField(obj.getClass(), key);
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

    @Nullable
    @SuppressWarnings("unchecked")
    public static <T> T getStaticField(Class<?> clazz, String key) throws ReflectiveOperationException {
        Field field = getClassField(clazz, key);
        try {
            boolean accessible = field.isAccessible();
            field.setAccessible(true);
            Object result = field.get(null);
            field.setAccessible(accessible);
            return (T) result;
        } catch (ReflectiveOperationException ignored) {
            return null;
        }
    }

    public static void setObjectField(Object obj, String key, Object value) throws ReflectiveOperationException {
        Field field = getClassField(obj.getClass(), key);
        try {
            boolean accessible = field.isAccessible();
            field.setAccessible(true);
            field.set(obj, value);
            field.setAccessible(accessible);
        } catch (ReflectiveOperationException ignored) {
        }
    }

    public static void setStaticField(Class<?> clazz, String key, Object value) throws ReflectiveOperationException {
        Field field = getClassField(clazz, key);
        try {
            boolean accessible = field.isAccessible();
            field.setAccessible(true);
            field.set(null, value);
            field.setAccessible(accessible);
        } catch (ReflectiveOperationException ignored) {
        }
    }
}
