package me.xpyex.plugin.xplib.bukkit.util.strings;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class MsgUtil {
    public static String getColorMsg(String msg) {
        if (msg == null || msg.trim().isEmpty()) return "";

        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    public static void sendActionBar(Player p, String text) {
        try {
            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(getColorMsg(text)));
        } catch (Throwable e) {
            p.sendMessage(getColorMsg(text));
        }
    }
}
