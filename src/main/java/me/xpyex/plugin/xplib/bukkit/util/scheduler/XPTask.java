package me.xpyex.plugin.xplib.bukkit.util.scheduler;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Function;
import me.xpyex.plugin.xplib.bukkit.api.Pair;
import me.xpyex.plugin.xplib.bukkit.util.value.ValueUtil;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class XPTask {
    private final AtomicReference<Object> result = new AtomicReference<>();
    private final ArrayList<Pair<TaskType, Object>> tasks = new ArrayList<>();  //type, Task
    private boolean isRunning = false;

    private XPTask() {

    }

    private XPTask(Object arg) {
        result.set(arg);
        //
    }

    private XPTask(TaskType type, Runnable func) {
        ValueUtil.notNull("参数不应为null", type, func);
        tasks.add(Pair.of(type, func));
    }

    private XPTask(TaskType type, Callable<?> func) {
        ValueUtil.notNull("参数不应为null", type, func);
        tasks.add(Pair.of(type, func));
    }

    @Contract(" -> new")
    public static @NotNull XPTask of() {
        return new XPTask();
        //
    }

    @Contract("_ -> new")
    public static @NotNull XPTask of(Object arg) {
        return new XPTask(arg);
        //
    }

    @Contract("_, _ -> new")
    public static @NotNull XPTask of(TaskType type, Runnable runnable) {
        return new XPTask(type, runnable);
        //
    }

    @Contract("_, _ -> new")
    public static @NotNull XPTask of(TaskType type, Callable<?> callable) {
        return new XPTask(type, callable);
        //
    }

    public XPTask run() {
        ValueUtil.mustTrue("错误: 该任务在执行前就已被加载过", result.get() == null);
        return this;
    }

    public void setRunning(boolean running) {
        isRunning = running;
        //
    }

    public XPTask then(TaskType type, Runnable runnable) {
        ValueUtil.notNull("参数不应为null", runnable);
        tasks.add(Pair.of(type, runnable));
        return this;
    }

    public <T> XPTask thenWithResult(TaskType type, Class<T> resultType, Callable<T> callable) {
        ValueUtil.notNull("参数不应为null", callable);
        tasks.add(Pair.of(type, callable));
        return this;
    }

    public <T> XPTask thenUsingLastResult(TaskType type, Class<T> lastResultType, Consumer<T> consumer) {
        ValueUtil.notNull("参数不应为null", consumer);
        tasks.add(Pair.of(type, consumer));
        return this;
    }

    public <T, R> XPTask thenUsingLastResultWithResult(TaskType type, Class<T> lastResultType, Class<R> resultType, Function<T, R> function) {
        ValueUtil.notNull("参数不应为null", function);
        tasks.add(Pair.of(type, function));
        return this;
    }

    public enum TaskType {
        SYNC,
        ASYNC
    }
}
