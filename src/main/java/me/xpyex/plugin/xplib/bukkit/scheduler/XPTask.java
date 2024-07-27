package me.xpyex.plugin.xplib.bukkit.scheduler;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;
import me.xpyex.plugin.xplib.XPLib;
import me.xpyex.plugin.xplib.api.Pair;
import me.xpyex.plugin.xplib.api.TryCallable;
import me.xpyex.plugin.xplib.api.TryConsumer;
import me.xpyex.plugin.xplib.api.TryFunction;
import me.xpyex.plugin.xplib.api.TryRunnable;
import me.xpyex.plugin.xplib.util.value.ValueUtil;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class XPTask implements Cloneable {
    private final Object initObj;
    private final AtomicReference<Object> result = new AtomicReference<>();
    private final ArrayList<Pair<TaskType, Object>> tasks = new ArrayList<>();  //type, Task
    private int isRunning = -999;

    private XPTask() {
        initObj = null;
    }

    private XPTask(Object arg) {
        initObj = arg;
        result.set(arg);
    }

    private XPTask(TaskType type, TryRunnable func) {
        initObj = null;
        ValueUtil.notNull("参数不应为null", type, func);
        tasks.add(Pair.of(type, func));
    }

    private XPTask(TaskType type, TryCallable<?> func) {
        initObj = null;
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
    public static @NotNull XPTask of(TaskType type, TryRunnable runnable) {
        return new XPTask(type, runnable);
        //
    }

    @Contract("_, _ -> new")
    public static @NotNull XPTask of(TaskType type, TryCallable<?> callable) {
        return new XPTask(type, callable);
        //
    }

    private void reset() {
        isRunning = -999;
        result.set(initObj);
    }

    private void runSingleTask() {
        if (isRunning >= tasks.size() - 1) {
            reset();
            return;
        }
        final Pair<TaskType, Object> pair = tasks.get(isRunning);
        BukkitRunnable bkTask = new BukkitRunnable() {
            @Override
            @SuppressWarnings("unchecked")
            public void run() {
                try {
                    if (pair.getValue() instanceof TryRunnable) {
                        ((TryRunnable) pair.getValue()).run();
                    } else if (pair.getValue() instanceof TryCallable) {
                        result.set(((TryCallable<?>) pair.getValue()).call());
                    } else if (pair.getValue() instanceof TryConsumer) {
                        ((TryConsumer<Object>) pair.getValue()).accept(result.get());
                    } else if (pair.getValue() instanceof TryFunction) {
                        result.set(((TryFunction<Object, ?>) pair.getValue()).apply(result.get()));
                    }
                } catch (InterruptedException ignored) {
                    reset();
                    return;
                } catch (Throwable e) {
                    e.printStackTrace();
                    reset();
                    return;
                }
                isRunning++;
                runSingleTask();
            }
        };
        if (pair.getKey() == TaskType.SYNC) {
            bkTask.runTask(XPLib.getInstance());
        } else if (pair.getKey() == TaskType.ASYNC) {
            bkTask.runTaskAsynchronously(XPLib.getInstance());
        } else {
            throw new IllegalArgumentException("错误的任务时态");
        }
    }

    public XPTask run() {
        ValueUtil.mustTrue("没有添加任何任务", !tasks.isEmpty());
        ValueUtil.mustTrue("任务已经在运行了", isRunning == -999);
        isRunning = 0;
        runSingleTask();
        return this;
    }

    public XPTask then(TaskType type, TryRunnable runnable) {
        ValueUtil.notNull("参数不应为null", runnable);
        tasks.add(Pair.of(type, runnable));
        return this;
    }

    public <T> XPTask thenWithResult(TaskType type, Class<T> resultType, TryCallable<T> callable) {
        ValueUtil.notNull("参数不应为null", callable);
        tasks.add(Pair.of(type, callable));
        return this;
    }

    public <T> XPTask thenUsingLastResult(TaskType type, Class<T> lastResultType, TryConsumer<T> consumer) {
        ValueUtil.notNull("参数不应为null", consumer);
        tasks.add(Pair.of(type, consumer));
        return this;
    }

    public <T, R> XPTask thenUsingLastResultWithResult(TaskType type, Class<T> lastResultType, Class<R> resultType, TryFunction<T, R> function) {
        ValueUtil.notNull("参数不应为null", function);
        tasks.add(Pair.of(type, function));
        return this;
    }

    @Override
    public XPTask clone() {
        try {
            XPTask cloned = (XPTask) super.clone();
            cloned.reset();
            return cloned;
        } catch (CloneNotSupportedException ignored) {
            XPTask cloned = of(initObj);
            cloned.tasks.addAll(tasks);
            return cloned;
        }
    }

    public enum TaskType {
        SYNC,
        ASYNC
    }
}
