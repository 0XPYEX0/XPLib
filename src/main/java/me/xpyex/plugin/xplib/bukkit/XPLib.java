package me.xpyex.plugin.xplib.bukkit;

import me.xpyex.plugin.xplib.bukkit.inventory.HandleMenu;
import me.xpyex.plugin.xplib.bukkit.inventory.Menu;
import me.xpyex.plugin.xplib.bukkit.util.bstats.BStatsUtil;
import me.xpyex.plugin.xplib.bukkit.util.config.ConfigUtil;
import org.bukkit.plugin.java.JavaPlugin;

public final class XPLib extends JavaPlugin {
    private static XPLib INSTANCE;

    @Override
    public void onEnable() {
        INSTANCE = this;
        saveResource("zh_cn.json", false);
        getServer().getPluginManager().registerEvents(new HandleMenu(), getInstance());
        getCommand("XPLib").setExecutor(((sender, command, label, args) -> {
            if (sender.hasPermission("XPLib.admin")) {
                if (args.length != 0) {
                    if (args[0].equalsIgnoreCase("reload")) {
                        ConfigUtil.reload();
                    }
                }
            }
            return true;
        }));
        getServer().getScheduler().runTaskTimerAsynchronously(getInstance(), () -> {
            for (Menu menu : Menu.getMenus().values()) {
                menu.updateInventory();
            }
        }, 0L, 5L);
        getServer().getScheduler().runTaskAsynchronously(getInstance(), () -> {
            BStatsUtil.hookWith();
            getLogger().info("与bStats挂钩");
        });
        getLogger().info("已加载");
    }

    @Override
    public void onDisable() {
        for (Menu menu : Menu.getMenus().values()) {
            menu.getPlayer().closeInventory();
        }
        getLogger().info("已卸载");
    }

    public static XPLib getInstance() {
        return INSTANCE;
        //
    }
}
