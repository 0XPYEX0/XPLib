package me.xpyex.plugin.xplib.bukkit.inventory.button;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public interface ButtonClickEffect {
    public void click(Player player, ClickType action, ItemStack i);
}
