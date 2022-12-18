package me.xpyex.plugin.xplib.bukkit;

import me.xpyex.plugin.xplib.bukkit.inventory.HandleMenu;
import me.xpyex.plugin.xplib.bukkit.inventory.Menu;
import org.bukkit.plugin.java.JavaPlugin;

public final class XPLib extends JavaPlugin {
    private static XPLib INSTANCE;

    @Override
    public void onEnable() {
        INSTANCE = this;
        saveResource("zh_cn.json", false);
        getServer().getPluginManager().registerEvents(new HandleMenu(), getInstance());
        getServer().getScheduler().runTaskTimerAsynchronously(getInstance(), () -> {
            for (Menu menu : Menu.getMenus().values()) {
                menu.updateInventory();
            }
        }, 0L, 5L);
        getLogger().info("已加载");
    }

    @Override
    public void onDisable() {
        getLogger().info("已卸载");
        //
    }

    public static XPLib getInstance() {
        return INSTANCE;
        //
    }
}
