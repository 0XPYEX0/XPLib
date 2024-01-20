package me.xpyex.plugin.xplib.bukkit.util.reflect;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import me.xpyex.plugin.xplib.bukkit.util.RootUtil;
import me.xpyex.plugin.xplib.bukkit.util.value.ValueUtil;
import org.jetbrains.annotations.NotNull;

public class MethodUtil extends RootUtil {
    @NotNull
    public static Method getMethod(Class<?> clazz, String name, Class<?>... parmaTypes) throws ReflectiveOperationException {
        ValueUtil.notEmpty("参数不应为空值", clazz, name);
        try {
            return clazz.getDeclaredMethod(name, parmaTypes);
        } catch (ReflectiveOperationException ignored) {
            if (clazz.getSuperclass() != null)
                return getMethod(clazz.getSuperclass(), name, parmaTypes);
            String[] typeStr = new String[parmaTypes.length];
            for (int i = 0; i < parmaTypes.length; i++) {
                typeStr[i] = parmaTypes[i].getSimpleName();
            }
            throw new NoSuchMethodException(clazz.getSimpleName() + " 类中不存在方法 " + name + Arrays.toString(typeStr).replace("[", "(").replace("]", ")"));
        }
    }

    @NotNull
    @SuppressWarnings("unchecked")
    public static <T> T executeInstanceMethod(Object obj, String method, Object... parma) throws ReflectiveOperationException {
        ValueUtil.notNull("执行方法的对象或方法名为null", obj, method);
        ArrayList<Class<?>> list = new ArrayList<>();
        for (Object o : parma) {
            list.add(o.getClass());
        }
        Method objMethod = getMethod(obj.getClass(), method, list.toArray(new Class[0]));
        boolean accessible = objMethod.isAccessible();
        objMethod.setAccessible(true);
        Object result = objMethod.invoke(obj, parma);
        objMethod.setAccessible(accessible);
        return (T) result;
    }

    @NotNull
    @SuppressWarnings("unchecked")
    public static <T> T executeClassMethod(Class<?> clazz, String method, Object... parma) throws ReflectiveOperationException {
        ValueUtil.notNull("执行静态方法的目标类或方法名为null", clazz, method);
        ArrayList<Class<?>> list = new ArrayList<>();
        for (Object o : parma) {
            list.add(o.getClass());
        }
        Method classMethod = getMethod(clazz, method, list.toArray(new Class[0]));
        boolean accessible = classMethod.isAccessible();
        classMethod.setAccessible(true);
        Object result = classMethod.invoke(null, parma);
        classMethod.setAccessible(accessible);
        return (T) result;
    }

    @NotNull
    public static <T> T executeClassMethod(String className, String method, Object... parma) throws ReflectiveOperationException {
        return executeClassMethod(ClassUtil.getClass(className, false, false), method, parma);
        //
    }
}
