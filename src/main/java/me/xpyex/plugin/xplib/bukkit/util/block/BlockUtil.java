package me.xpyex.plugin.xplib.bukkit.util.block;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import me.xpyex.plugin.xplib.bukkit.XPLib;
import me.xpyex.plugin.xplib.bukkit.util.Util;
import me.xpyex.plugin.xplib.bukkit.util.version.VersionUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class BlockUtil {
    /**
     * 覆盖从loc1到loc2内的所有方块，且线程安全 (不异步执行)
     *
     * @param loc1 第一个坐标
     * @param loc2 第二个坐标
     * @param type 将被替换的方块类型
     */
    public static void fillBlocks(Location loc1, Location loc2, Material type) {
        for (Block block : getBlocks(loc1, loc2)) {
            Bukkit.getScheduler().runTask(XPLib.getInstance(), () -> {
                block.setType(type);
            });
        }
    }

    /**
     * 覆盖从loc1到loc2内的所有方块，且线程安全 (不异步执行)
     *
     * @param loc1 第一个坐标
     * @param loc2 第二个坐标
     * @param data 将被写入的BlockData (仅1.13+)
     */
    public static void fillBlocks(Location loc1, Location loc2, BlockData data) {
        if (VersionUtil.getMainVersion() < 13) {
            throw new IllegalStateException("该方法仅在 MC版本 >= 1.13 时可用");
        }
        for (Block block : getBlocks(loc1, loc2)) {
            Bukkit.getScheduler().runTask(XPLib.getInstance(), () -> {
                block.setBlockData(data);
            });
        }
    }

    /**
     * 替换从loc1到loc2内的所有方块，且线程安全 (不异步执行)
     *
     * @param loc1 第一个坐标
     * @param loc2 第二个坐标
     * @param what 会被替换掉的方块类型
     * @param data 将被写入的BlockData (仅1.13+)
     */
    public static void replaceBlocks(Location loc1, Location loc2, Material what, BlockData data) {
        if (VersionUtil.getMainVersion() < 13) {
            throw new IllegalStateException("该方法仅在 MC版本 >= 1.13 时可用");
        }
        for (Block block : getBlocks(loc1, loc2)) {
            if (block.getType() == what) {
                Bukkit.getScheduler().runTask(XPLib.getInstance(), () -> {
                    block.setBlockData(data);
                });
            }
        }
    }

    /**
     * 替换从loc1到loc2内的所有方块，且线程安全 (不异步执行)
     *
     * @param loc1 第一个坐标
     * @param loc2 第二个坐标
     * @param what 会被替换掉的方块类型
     * @param type 将要替换的方块类型
     */
    public static void replaceBlocks(Location loc1, Location loc2, Material what, Material type) {
        for (Block block : getBlocks(loc1, loc2)) {
            if (block.getType() == what) {
                Bukkit.getScheduler().runTask(XPLib.getInstance(), () -> {
                    block.setType(type);
                });
            }
        }
    }

    /**
     * 获取从loc1到loc2内的所有方块
     *
     * @param loc1 第一个坐标
     * @param loc2 第二个坐标
     * @return 两坐标内的所有方块
     */
    @NotNull
    public static List<Block> getBlocks(Location loc1, Location loc2) {
        if (Util.isNull(loc1, loc2) || !loc1.isWorldLoaded() || !loc2.isWorldLoaded()) {
            return Collections.emptyList();
        }
        if (loc1.equals(loc2)) {
            return Collections.singletonList(loc1.getBlock());
        }
        if (loc1.getWorld().equals(loc2.getWorld())) {
            ArrayList<Block> blocks = new ArrayList<>();
            int x1 = Math.min(loc1.getBlockX(), loc2.getBlockX());
            int x2 = Math.max(loc1.getBlockX(), loc2.getBlockX());
            int y1 = Math.min(loc1.getBlockY(), loc2.getBlockY());
            int y2 = Math.max(loc1.getBlockY(), loc2.getBlockY());
            int z1 = Math.min(loc1.getBlockZ(), loc2.getBlockZ());
            int z2 = Math.max(loc1.getBlockZ(), loc2.getBlockZ());

            for (int x = x1; x <= x2; x++) {
                for (int y = y1; y <= y2; y++) {
                    for (int z = z1; z <= z2; z++) {
                        blocks.add(new Location(loc1.getWorld(), x, y, z).getBlock());
                    }
                }
            }
            return blocks;
        }
        return Collections.emptyList();
    }

    /**
     * 获取玩家看向的方块
     *
     * @param player 目标玩家
     * @return player看向的方块
     */
    @NotNull
    public static Block getTargetBlock(Player player) {
        return player.getTargetBlock(null, 10);
        //
    }
}
