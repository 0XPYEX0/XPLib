package me.xpyex.plugin.xplib.bukkit.inventory.button;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

public interface ButtonCondition extends Function<Player, Integer> {
}
