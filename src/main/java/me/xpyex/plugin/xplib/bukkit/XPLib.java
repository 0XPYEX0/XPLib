package me.xpyex.plugin.xplib.bukkit;

import org.bukkit.plugin.java.JavaPlugin;

public final class XPLib extends JavaPlugin {
    private static XPLib INSTANCE;

    @Override
    public void onEnable() {
        INSTANCE = this;
        saveResource("zh_cn.json", false);
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
