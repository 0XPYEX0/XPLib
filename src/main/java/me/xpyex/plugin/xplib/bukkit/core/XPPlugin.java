package me.xpyex.plugin.xplib.bukkit.core;

import com.google.gson.JsonObject;
import java.io.File;
import java.util.Locale;
import java.util.UUID;
import java.util.WeakHashMap;
import me.xpyex.plugin.xplib.bukkit.api.NationalMessage;
import me.xpyex.plugin.xplib.bukkit.bstats.Metrics;
import me.xpyex.plugin.xplib.bukkit.util.config.ConfigUtil;
import me.xpyex.plugin.xplib.bukkit.util.config.GsonUtil;
import me.xpyex.plugin.xplib.bukkit.util.value.ValueUtil;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class XPPlugin extends JavaPlugin {
    private final WeakHashMap<UUID, File> playerConfigFileCache = new WeakHashMap<>();
    private final File configFile = new File(getDataFolder(), "config.json");
    private JsonObject defaultConfig = null;
    private JsonObject defaultPlayerConfig = null;
    private boolean registeredDefaultPConfig = false;

    public Metrics hookBStats(int id) {
        return new Metrics(this, id);
        //
    }

    public void registerCmd(String command, @Nullable CommandExecutor executor, @Nullable TabCompleter completer) throws IllegalArgumentException {
        PluginCommand cmd = this.getCommand(command);
        ValueUtil.notNull("未在 plugin.yml 内注册命令: " + command, cmd);
        assert cmd != null;

        cmd.setExecutor(executor);
        cmd.setTabCompleter(completer);
    }

    public void registerCmd(String cmd, @NotNull TabCompleter completer) {
        registerCmd(cmd, null, completer);
        //
    }

    public void registerCmd(String cmd, @NotNull CommandExecutor executor) {
        registerCmd(cmd, executor, null);
        //
    }

    public void registerListener(Listener listener) {
        this.getServer().getPluginManager().registerEvents(listener, this);
        //
    }

    public void setDefaultConfig(JsonObject defaultConfig) {
        this.defaultConfig = defaultConfig;
        //
    }

    public void setDefaultPlayerConfig(JsonObject defaultPlayerConfig) {
        this.defaultPlayerConfig = defaultPlayerConfig;
        if (!registeredDefaultPConfig) {
            registeredDefaultPConfig = true;
            registerListener(new Listener() {
                @EventHandler
                public void onJoin(PlayerJoinEvent event) {
                    ConfigUtil.saveConfig(XPPlugin.this, "players/" + event.getPlayer().getUniqueId() + ".json", GsonUtil.parseStr(defaultPlayerConfig), false);
                }
            });
        }
    }

    @Override
    public void saveDefaultConfig() {
        getServer().getScheduler().runTaskAsynchronously(this, () -> {
            if (defaultConfig != null) {
                ConfigUtil.saveConfig(this, "config", GsonUtil.parseStr(defaultConfig), false);
            }
            if (defaultPlayerConfig != null) {
                for (Player player : getServer().getOnlinePlayers()) {
                    ConfigUtil.saveConfig(this, "players/" + player.getUniqueId() + ".json", GsonUtil.parseStr(defaultPlayerConfig), false);
                }
            }
        });
    }

    public boolean hasConfig(Player player) {
        return getConfigFile(player).exists() && !getConfigFile(player).isDirectory();
        //
    }

    public File getConfigFile(Player player) {
        if (!playerConfigFileCache.containsKey(player.getUniqueId()))
            playerConfigFileCache.put(player.getUniqueId(), new File(getDataFolder(), "players/" + player.getUniqueId() + ".json"));
        return playerConfigFileCache.get(player.getUniqueId());
    }

    public File getConfigFile() {
        return configFile;
        //
    }

    public JsonObject getJsonConfig(String path) {
        return ConfigUtil.getConfig(this, path);
        //
    }

    public <T> T getJsonConfig(String path, Class<T> type) {
        return ConfigUtil.getConfig(this, path, type);
        //
    }

    public JsonObject getJsonConfig() {
        return ConfigUtil.getConfig(this);
        //
    }

    public <T> T getJsonConfig(Class<T> type) {
        return ConfigUtil.getConfig(this, type);
        //
    }

    public String getI18N(String key) {
        return getI18N(Locale.getDefault(), key);
        //
    }

    public String getI18N(Locale locale, String key) {
        NationalMessage config = ConfigUtil.getConfig(this, "messages/" + locale.getLanguage(), NationalMessage.class);
        return "Uncompleted";
    }
}
