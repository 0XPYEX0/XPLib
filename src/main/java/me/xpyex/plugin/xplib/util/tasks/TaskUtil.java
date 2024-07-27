package me.xpyex.plugin.xplib.util.tasks;

import me.xpyex.plugin.xplib.XPLib;
import me.xpyex.plugin.xplib.api.TryCallable;
import me.xpyex.plugin.xplib.api.TryRunnable;
import me.xpyex.plugin.xplib.util.RootUtil;
import me.xpyex.plugin.xplib.util.strings.MsgUtil;
import me.xpyex.plugin.xplib.util.value.ValueUtil;

public class TaskUtil extends RootUtil {

    /**
     * 当需要获取值，而方法可能抛出错误时，使用该方法重复获取.
     *
     * @param callable    获取值的方法体
     * @param repeatTimes 重复获取的次数，超出次数则返回null. 填入0则不限制获取次数，将不断尝试直至取到值. 填入0时请注意安全，避免堵塞主线程！
     * @return 返回需要获取的值
     */
    public static <T> T repeatIfError(TryCallable<T> callable, long repeatTimes) {
        return repeatIfError(callable, repeatTimes, 0);
        //
    }

    /**
     * 当需要获取值，而方法可能抛出错误时，使用该方法重复获取.
     *
     * @param callable    获取值的方法体
     * @param repeatTimes 重复获取的次数，超出次数则返回null. 填入0则不限制获取次数，将不断尝试直至取到值. 填入0时请注意安全，避免堵塞主线程！
     * @param waitMillis  当出现错误时，等待多久(单位: 毫秒)再次执行. 若为0则不等待. 填入非0时请注意安全，避免在主线程等待.
     * @return 返回需要获取的值
     */
    public static <T> T repeatIfError(TryCallable<T> callable, long repeatTimes, long waitMillis) {
        ValueUtil.notNull("该执行的内容不为空", callable);
        ValueUtil.mustTrue("repeatTimes不应为负数", repeatTimes < 0);
        if (repeatTimes == 0) {
            while (true) {
                try {
                    return callable.call();
                } catch (Throwable e) {
                    MsgUtil.debugLog(XPLib.getInstance(), e);
                    if (waitMillis > 0) {
                        try {
                            Thread.sleep(waitMillis);
                        } catch (Throwable ignored1) {
                        }
                    }
                }
            }
        } else {
            for (int i = 0; i < repeatTimes; i++) {
                try {
                    return callable.call();
                } catch (Throwable e) {
                    MsgUtil.debugLog(XPLib.getInstance(), e);
                    if (waitMillis > 0) {
                        try {
                            Thread.sleep(waitMillis);
                        } catch (Throwable ignored1) {
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * 当需要执行方法，而方法有可能抛出错误时，使用该方法重复执行
     *
     * @param r           需要执行的方法体
     * @param repeatTimes 重复获取的次数，超出次数则返回null. 填入0则不限制获取次数，将不断尝试直至取到值. 填入0时请注意安全，避免堵塞主线程！
     */
    public static void repeatIfError(TryRunnable r, long repeatTimes) {
        repeatIfError(() -> {
            r.run();
            return null;
        }, repeatTimes);
    }
}
