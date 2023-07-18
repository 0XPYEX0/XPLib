package me.xpyex.plugin.xplib.bukkit.util.reflect;

import java.lang.reflect.Method;
import java.util.ArrayList;
import me.xpyex.plugin.xplib.bukkit.util.RootUtil;
import me.xpyex.plugin.xplib.bukkit.util.value.ValueUtil;
import org.jetbrains.annotations.Nullable;

public class MethodUtil extends RootUtil {
    @Nullable
    public static Method getMethod(Class<?> clazz, String name, Class<?>... parmaTypes) {
        ValueUtil.notEmpty("参数不应为空值", clazz, name);
        try {
            return clazz.getDeclaredMethod(name, parmaTypes);
        } catch (ReflectiveOperationException ignored) {
            if (clazz.getSuperclass() != null)
                return getMethod(clazz.getSuperclass(), name, parmaTypes);
        }
        return null;
    }

    @Nullable
    @SuppressWarnings("unchecked")
    public static <T> T executeMethod(Object obj, String method, Object... parma) {
        ValueUtil.notNull("执行方法的对象或方法名为null", obj, method);
        ArrayList<Class<?>> list = new ArrayList<>();
        for (Object o : parma) {
            list.add(o.getClass());
        }
        try {
            Method objMethod = getMethod(obj.getClass(), method, list.toArray(new Class[0]));
            ValueUtil.notNull("类 " + obj.getClass().getSimpleName() + " 内不存在方法 " + method, objMethod);
            assert objMethod != null;

            boolean accessible = objMethod.isAccessible();
            objMethod.setAccessible(true);
            Object result = objMethod.invoke(obj, parma);
            objMethod.setAccessible(accessible);

            return (T) result;
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Nullable
    @SuppressWarnings("unchecked")
    public static <T> T executeStaticMethod(Class<?> clazz, String method, Object... parma) {
        ValueUtil.notNull("执行静态方法的目标类或方法名为null", clazz, method);
        ArrayList<Class<?>> list = new ArrayList<>();
        for (Object o : parma) {
            list.add(o.getClass());
        }
        try {
            Method classMethod = getMethod(clazz, method, list.toArray(new Class[0]));
            ValueUtil.notNull("类 " + clazz.getSimpleName() + " 内不存在方法 " + method, classMethod);
            assert  classMethod != null;

            boolean accessible = classMethod.isAccessible();
            classMethod.setAccessible(true);
            Object result = classMethod.invoke(null, parma);
            classMethod.setAccessible(accessible);

            return (T) result;
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
            return null;
        }
    }
}
