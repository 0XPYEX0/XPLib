package me.xpyex.plugin.xplib.bukkit.api;

import java.util.ArrayList;
import me.xpyex.plugin.xplib.bukkit.util.Util;
import me.xpyex.plugin.xplib.bukkit.util.strings.MsgUtil;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

/**
 * 命令帮助菜单
 * 替开发者完成帮助菜单的任务
 */
public class CommandMenu {
    private final String command;
    private final char cmdColor;
    private final char helpColor;
    private final ArrayList<Pair<String, String>> helpList = new ArrayList<>();
    private String start = null;
    private String hook = null;
    private String end = null;

    /**
     * 构造函数
     *
     * @param command 帮助的主命令
     */
    public CommandMenu(String command) {
        this(command, 'f', 'f');
        //
    }

    public CommandMenu(String command, char cmdColor, char helpColor) {
        this(command, cmdColor, helpColor, null, null, null);
        //
    }

    /**
     * 构造函数
     *
     * @param command   帮助菜单的主命令
     * @param cmdColor  命令部分以哪个字符染色
     * @param helpColor 帮助部分由哪个字符染色
     * @param start     消息开头使用的格式
     * @param hook      消息中间使用的格式
     * @param end       消息结尾使用的格式
     */
    public CommandMenu(String command, char cmdColor, char helpColor, String start, String hook, String end) {
        this.command = command;
        this.cmdColor = cmdColor;
        this.helpColor = helpColor;
        this.start = start;
        this.hook = hook;
        this.end = end;
    }

    /**
     * 添加一行帮助
     *
     * @param argument 参数
     * @param help     对应参数的帮助
     * @return 返回自身，制造链式调用
     */
    @NotNull
    public CommandMenu add(String argument, String help) {
        if (argument == null || help == null || argument.isEmpty()) {  //help可为空值，仅描述有子参数，但不提供教程
            return this;
        }
        helpList.add(new Pair<>(argument, help));
        return this;
    }

    /**
     * 拼接整个帮助菜单
     *
     * @return 返回帮助菜单
     */
    @Override
    public String toString() {
        if (Util.isEmpty(hook)) {
            hook = "&f-";
        }
        CommandMessager messager = new CommandMessager();
        for (Pair<String, String> pair : helpList) {
            messager.plus(MsgUtil.getColorMsg(Util.getOrDefault(start, "") + "&" + cmdColor + "/" + command + " " + pair.getKey() + " " + Util.getOrDefault(hook, "&f-") + " &" + helpColor + pair.getValue() + Util.getOrDefault(hook, "")));
        }
        return messager.toString();
    }

    /**
     * 直接发送该实例
     *
     * @param target 接收者
     */
    public void send(CommandSender target) {
        target.sendMessage(this.toString());
        //
    }
}
