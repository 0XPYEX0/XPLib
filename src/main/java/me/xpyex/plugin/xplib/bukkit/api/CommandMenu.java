package me.xpyex.plugin.xplib.bukkit.api;

import java.util.ArrayList;
import me.xpyex.plugin.xplib.bukkit.util.strings.MsgUtil;
import org.bukkit.command.CommandSender;

/**
 * 命令帮助菜单
 * 替开发者完成帮助菜单的任务
 */
public class CommandMenu {
    private final String command;
    private final String cmdColor;
    private final String helpColor;
    private final ArrayList<Pair<String, String>> helpList = new ArrayList<>();

    /**
     * 构造函数
     * @param command 帮助的主命令
     */
    public CommandMenu(String command, String cmdColor, String helpColor) {
        this.command = command;
        this.cmdColor = cmdColor;
        this.helpColor = helpColor;
    }

    /**
     * 添加一行帮助
     * @param argument 参数
     * @param help 对应参数的帮助
     * @return 返回自身，制造链式调用
     */
    public CommandMenu add(String argument, String help) {
        if (argument == null || help == null || argument.isEmpty()) {  //help可为空值，仅描述有子参数，但不提供教程
            return this;
        }
        helpList.add(new Pair<>(argument, help));
        return this;
    }

    /**
     * 拼接整个帮助菜单
     * @return 返回帮助菜单
     */
    @Override
    public String toString() {
        CommandMessager messager = new CommandMessager();
        for (Pair<String, String> pair : helpList) {
            messager.plus(MsgUtil.getColorMsg("&" + cmdColor + "/" + command + " " + pair.getKey() + " &f- &" + helpColor + pair.getValue()));
        }
        return messager.toString();
    }

    public void send(CommandSender target) {
        target.sendMessage(this.toString());
        //
    }
}
