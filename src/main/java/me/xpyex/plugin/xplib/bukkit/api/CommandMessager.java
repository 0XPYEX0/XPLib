package me.xpyex.plugin.xplib.bukkit.api;

import java.util.ArrayList;
import me.xpyex.plugin.xplib.bukkit.util.strings.MsgUtil;
import org.bukkit.command.CommandSender;

/**
 * 消息列表
 * 帮助开发者快速地发送换行消息
 */
public class CommandMessager {
    private final ArrayList<String> messages = new ArrayList<>();

    public CommandMessager() {
    }

    public CommandMessager(String message) {
        messages.add(message);
        //
    }

    /**
     * 新增一行
     *
     * @param message 参数
     * @return 返回自身，制造链式
     */
    public CommandMessager plus(String message) {
        messages.add(message);
        return this;
    }

    /**
     * 获取最终结果
     *
     * @return 字符串结果
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (String argument : messages) {
            result.append(argument).append("\n");
        }
        return result.substring(0, result.length() - 1);
    }

    /**
     * 直接发送给目标
     *
     * @param target 接收信息的目标
     */
    public void send(CommandSender target) {
        target.sendMessage(MsgUtil.getColorMsg(this.toString()));
        //
    }
}
