package me.xpyex.plugin.xplib.bukkit;

import java.io.IOException;
import me.xpyex.plugin.xplib.bukkit.config.ConfigUtil;
import me.xpyex.plugin.xplib.bukkit.core.XPPlugin;
import me.xpyex.plugin.xplib.bukkit.inventory.HandleMenu;
import me.xpyex.plugin.xplib.bukkit.inventory.Menu;
import me.xpyex.plugin.xplib.bukkit.value.ValueCacheUtil;
import me.xpyex.plugin.xplib.util.files.FileUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;

public final class XPLib extends XPPlugin {
    private static XPLib INSTANCE;

    public static XPLib getInstance() {
        return INSTANCE;
        //
    }

    @Override
    public void onDisable() {
        for (Menu menu : Menu.getMenus().values()) {
            menu.getPlayer().closeInventory();
        }
        getLogger().info("已卸载");
    }

    @Override
    public void onEnable() {
        INSTANCE = this;
        getLogger().info("当前服务端核心版本: " + getServer().getBukkitVersion());
        saveResource("minecraft/zh_cn.json", false);
        try {
            if (!getConfigFile().exists())
                FileUtil.writeFile(getConfigFile(), "{\"Debug\": false}");
        } catch (IOException e) {
            e.printStackTrace();
        }
        registerListener(new HandleMenu());
        registerListener(new Listener() {
            @EventHandler
            public void onPluginUnload(PluginDisableEvent event) {
                ValueCacheUtil.delData(event.getPlugin());
            }
        });
        registerCmd("XPLib", ((sender, command, label, args) -> {
            if (sender.hasPermission("XPLib.admin")) {
                if (args.length != 0) {
                    if (args[0].equalsIgnoreCase("reload")) {
                        ConfigUtil.reload();
                    }
                }
            }
            return true;
        }), null);
        getServer().getScheduler().runTaskTimerAsynchronously(getInstance(), () -> {
            for (Menu menu : Menu.getMenus().values()) {
                menu.updateInventory();
            }
        }, 0L, 5L);
        getServer().getScheduler().runTaskAsynchronously(getInstance(), () -> {
            hookBStats(17099);
            getLogger().info("与bStats挂钩");
        });
        registerListener(new Listener() {
            @EventHandler
            public void onPluginLoad(PluginEnableEvent event) {
                if (event.getPlugin() instanceof XPPlugin) {
                    event.getPlugin().saveDefaultConfig();
                }
            }
        });
        getLogger().info("已加载");
    }
}
