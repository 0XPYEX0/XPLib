package me.xpyex.plugin.xplib.bukkit.util.reflect;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.WeakHashMap;
import me.xpyex.plugin.xplib.bukkit.util.RootUtil;
import me.xpyex.plugin.xplib.bukkit.util.value.ValueUtil;
import org.jetbrains.annotations.NotNull;

public class MethodUtil extends RootUtil {
    private static final WeakHashMap<String, Method> METHOD_CACHE = new WeakHashMap<>();

    private static String[] getClassNames(Class<?>[] classes) {
        String[] typeStr = new String[classes.length];
        for (int i1 = 0; i1 < classes.length; i1++) {
            typeStr[i1] = classes[i1].getSimpleName();
        }
        return typeStr;
    }

    @NotNull
    public static Method getMethod(Class<?> clazz, String name, Class<?>... paramTypes) throws ReflectiveOperationException {
        ValueUtil.notEmpty("参数不应为空值", clazz, name);
        if (paramTypes == null) paramTypes = new Class<?>[0];
        String mapKey = clazz.getName() + "." + name + Arrays.toString(paramTypes);
        if (METHOD_CACHE.containsKey(mapKey)) {
            return METHOD_CACHE.get(mapKey);
        }
        Method resultMethod = null;
        try {
            resultMethod = clazz.getDeclaredMethod(name, paramTypes);
            METHOD_CACHE.put(mapKey, resultMethod);
            return resultMethod;
        } catch (NoSuchMethodException ignored) {
            checkMethod:
            for (Method declaredMethod : clazz.getDeclaredMethods()) {
                if (!name.equals(declaredMethod.getName())) {
                    continue;  //可以确定不是这个Method了
                }
                Class<?>[] targetMethodParmaTypes = declaredMethod.getParameterTypes();
                if (targetMethodParmaTypes.length != paramTypes.length) {
                    continue;  //可以确定不是这个Method了
                }

                checkParams:
                for (int i = 0; i < targetMethodParmaTypes.length; i++) {
                    Class<?> targetType = targetMethodParmaTypes[i];
                    Class<?> paramType = paramTypes[i];
                    if (!targetType.isAssignableFrom(paramType)) {  //param instanceof target
                        continue checkMethod;  //找下一个Method，不是这个
                    }
                }

                if (resultMethod != null) {  //如果参数也符合，看看有没有多个匹配的Method，找最合适的
                    if (resultMethod.equals(declaredMethod)) {
                        continue;
                    }
                    moreSuitable:
                    for (int i = 0; i < resultMethod.getParameterTypes().length; i++) {  //上一次找到的方法的参数类型
                        Class<?> lastParamType = resultMethod.getParameterTypes()[i];
                        Class<?> newParamType = declaredMethod.getParameterTypes()[i];
                        //如果newParamType instanceof lastParamType，则newParamType更适合，替换
                        if (newParamType.isAssignableFrom(lastParamType)) {  //lastParamType更适合，继续找下一个方法
                            continue checkMethod;
                        }
                    }
                }
                resultMethod = declaredMethod;  //参数长度、类型均匹配
            }
        }
        if (resultMethod != null) {
            METHOD_CACHE.put(mapKey, resultMethod);
            return resultMethod;
        }
        throw new NoSuchMethodException(clazz.getSimpleName() + " 类中不存在方法 " + name + Arrays.toString(getClassNames(paramTypes)).replace("[", "(").replace("]", ")"));
    }

    @NotNull
    public static Method AIgetMethod(Class<?> clazz, String name, Class<?>... paramTypes) throws ReflectiveOperationException {
        ValueUtil.notEmpty("参数不应为空值", clazz, name);

        // 优化缓存键的生成，避免将null参数类型表示为"null"字符串
        String mapKey = clazz.getName() + "." + name + Arrays.toString(paramTypes == null ? new Class<?>[0] : paramTypes);

        if (METHOD_CACHE.containsKey(mapKey)) {
            return METHOD_CACHE.get(mapKey);
        }

        Method resultMethod = null;
        try {
            resultMethod = clazz.getDeclaredMethod(name, paramTypes);
        } catch (NoSuchMethodException e) {
            // 这里捕获并处理NoSuchMethodException，区分了ReflectiveOperationException的不同类型
            // 其他ReflectiveOperationException类型的异常仍然会抛出，以便调用者能够处理
        }

        for (Method declaredMethod : clazz.getDeclaredMethods()) {
            if (!name.equals(declaredMethod.getName()) || declaredMethod.getParameterTypes().length != paramTypes.length) {
                continue;
            }

            boolean isMatch = true;
            for (int i = 0; i < declaredMethod.getParameterTypes().length; i++) {
                if (!declaredMethod.getParameterTypes()[i].isAssignableFrom(paramTypes[i])) {
                    isMatch = false;
                    break;
                }
            }

            if (isMatch) {
                if (declaredMethod.equals(resultMethod)) {
                    // 更简洁的逻辑来选择更合适的Method
                    resultMethod = declaredMethod;
                }
            }
        }

        if (resultMethod != null) {
            METHOD_CACHE.put(mapKey, resultMethod);
            return resultMethod;
        }

        // 提供更详细的错误信息
        throw new NoSuchMethodException(clazz.getName() + " 类中不存在方法 " + name + Arrays.toString(paramTypes).replace("[", "(").replace("]", ")"));
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
        return executeClassMethod(ClassUtil.getClass(className, true, false), method, parma);
        //
    }
}
