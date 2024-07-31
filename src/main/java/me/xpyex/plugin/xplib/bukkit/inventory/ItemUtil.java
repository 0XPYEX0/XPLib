package me.xpyex.plugin.xplib.bukkit.inventory;

import java.util.ArrayList;
import me.xpyex.plugin.xplib.bukkit.strings.MsgUtil;
import me.xpyex.plugin.xplib.util.RootUtil;
import me.xpyex.plugin.xplib.util.strings.StrUtil;
import me.xpyex.plugin.xplib.util.value.ValueUtil;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemUtil extends RootUtil {
    /**
     * 获取新的ItemStack
     *
     * @param stack 可复制ItemStack
     * @param name  修改显示名称
     * @param lore  修改Lore
     * @return 全新的ItemStack
     */
    public static ItemStack getItemStack(ItemStack stack, String name, String... lore) {
        ValueUtil.notNull("参数不应为null", stack, name, lore);
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

    /**
     * 获取新的ItemStack
     *
     * @param material 新的ItemStack的类型
     * @param name     修改显示名称
     * @param lore     修改Lore
     * @return 全新的ItemStack
     */
    public static ItemStack getItemStack(Material material, String name, String... lore) {
        ValueUtil.notNull("参数不应为null", material, name, lore);
        return getItemStack(new ItemStack(material), name, lore);
        //
    }

    /**
     * 检查两个ItemStack是否完全相等
     *
     * @param i1 第一个ItemStack
     * @param i2 第二个ItemStack
     * @return 是否相等
     */
    public static boolean equals(ItemStack i1, ItemStack i2) {
        if (ValueUtil.isNull(i1, i2)) {
            return false;
        }
        ItemStack copied1 = new ItemStack(i1);
        ItemStack copied2 = new ItemStack(i2);
        copied1.setAmount(1);
        copied2.setAmount(1);
        return copied1.toString().equals(copied2.toString());
    }

    /**
     * 检查target是否为某种类型之一
     *
     * @param target    目标类型
     * @param materials 待检查的类型
     * @return target是materials之一
     */
    public static boolean typeIsOr(Material target, Material... materials) {
        if (ValueUtil.isEmpty(target, materials)) {
            return false;
        }

        for (Material material : materials) {
            if (target == material) {
                return true;
            }
        }
        return false;
    }

    /**
     * 检查target是否为某种类型之一
     *
     * @param target    目标类型
     * @param materials 待检查的类型
     * @return target是materials之一
     */
    public static boolean typeIsOr(Material target, String... materials) {
        return StrUtil.equalsIgnoreCaseOr(target.toString(), materials);
        //
    }

    /**
     * 检查ItemStack的类型是否为某种类型之一
     *
     * @param stack     目标ItemStack
     * @param materials 待检查的类型
     * @return stack的类型是materials之一
     */
    public static boolean typeIsOr(ItemStack stack, String... materials) {
        return typeIsOr(stack.getType(), materials);
        //
    }

    /**
     * 检查ItemStack的类型是否为某种类型之一
     *
     * @param stack     目标ItemStack
     * @param materials 待检查的类型
     * @return stack的类型是materials之一
     */
    public static boolean typeIsOr(ItemStack stack, Material... materials) {
        return typeIsOr(stack.getType(), materials);
    }

    /**
     * 检查Block的类型是否为某种类型之一
     *
     * @param block     目标ItemStack
     * @param materials 待检查的类型
     * @return block的类型是materials之一
     */
    public static boolean typeIsOr(Block block, Material... materials) {
        return typeIsOr(block.getType(), materials);
        //
    }

    /**
     * 检查Block的类型是否为某种类型之一
     *
     * @param block     目标ItemStack
     * @param materials 待检查的类型
     * @return block的类型是materials之一
     */
    public static boolean typeIsOr(Block block, String... materials) {
        return typeIsOr(block.getType(), materials);
        //
    }
}
