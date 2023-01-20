package me.xpyex.plugin.xplib.bukkit.inventory.button;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

/**
 * 点击按钮所执行的方法体
 */
public interface ButtonClickEffect {
    public void click(Player player, ClickType action, ItemStack i);
}
