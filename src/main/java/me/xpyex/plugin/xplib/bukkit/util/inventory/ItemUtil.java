package me.xpyex.plugin.xplib.bukkit.util.inventory;

import java.util.ArrayList;
import me.xpyex.plugin.xplib.bukkit.util.strings.MsgUtil;
import me.xpyex.plugin.xplib.bukkit.util.strings.StrUtil;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemUtil {
    public static ItemStack getItemStack(ItemStack stack, String name, String... lore) {
        ItemStack out = new ItemStack(stack);
        ItemMeta meta = out.getItemMeta();
        meta.setDisplayName(MsgUtil.getColorMsg(name));
        ArrayList<String> list = new ArrayList<>();
        for (String s : lore) {
            list.add(MsgUtil.getColorMsg(s));
        }
        meta.setLore(list);
        out.setItemMeta(meta);
        return out;
    }

    public static ItemStack getItemStack(Material material, String name, String... lore) {
        return getItemStack(new ItemStack(material), name, lore);
        //
    }

    public static boolean equals(ItemStack i1, ItemStack i2) {
        ItemStack copied1 = new ItemStack(i1);
        ItemStack copied2 = new ItemStack(i2);
        copied1.setAmount(1);
        copied2.setAmount(1);
        return copied1.toString().equals(copied2.toString());
    }

    public static boolean typeIsOr(Material target, Material... materials) {
        if (target == null || materials == null || materials.length == 0) {
            return false;
        }

        for (Material material : materials) {
            if (target == material) {
                return true;
            }
        }
        return false;
    }

    public static boolean typeIsOr(Material target, String... materials) {
        return StrUtil.equalsIgnoreCaseOr(target.toString(), materials);
        //
    }

    public static boolean typeIsOr(ItemStack stack, String... materials) {
        return typeIsOr(stack.getType(), materials);
        //
    }

    public static boolean typeIsOr(ItemStack stack, Material... materials) {
        return typeIsOr(stack.getType(), materials);
    }

    public static boolean typeIsOr(Block block, Material... materials) {
        return typeIsOr(block.getType(), materials);
        //
    }

    public static boolean typeIsOr(Block block, String... materials) {
        return typeIsOr(block.getType(), materials);
        //
    }
}
