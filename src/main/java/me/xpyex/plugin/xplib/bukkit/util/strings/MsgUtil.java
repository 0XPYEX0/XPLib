package me.xpyex.plugin.xplib.bukkit.util.strings;

import me.xpyex.plugin.xplib.bukkit.util.config.ConfigUtil;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class MsgUtil {
    public static String getColorMsg(String msg) {
        if (msg == null) return "";  //不检查trim，允许返回空格字符串

        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    public static void sendActionBar(Player p, String text) {
        try {
            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(getColorMsg(text)));
        } catch (Throwable e) {
            p.sendMessage(getColorMsg(text));
        }
    }

    public static void debugLog(Plugin plugin, String info) {
        if (ConfigUtil.getConfig(plugin) != null && ConfigUtil.getConfig(plugin).has("Debug") && ConfigUtil.getConfig(plugin).get("Debug").getAsBoolean())
            plugin.getLogger().info("[Debug] " + info);
    }

    public static void debugLog(Plugin plugin, Throwable e) {
        if (ConfigUtil.getConfig(plugin) != null && ConfigUtil.getConfig(plugin).has("Debug") && ConfigUtil.getConfig(plugin).get("Debug").getAsBoolean())
            e.printStackTrace();
    }
}
