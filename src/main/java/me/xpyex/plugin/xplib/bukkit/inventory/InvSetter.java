package me.xpyex.plugin.xplib.bukkit.inventory;

import java.util.HashMap;
import me.xpyex.plugin.xplib.bukkit.inventory.button.Button;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * 快速创建Inventory的工具类
 */
public class InvSetter {
    private final HashMap<String, ItemStack> signMap = new HashMap<>();
    private final HashMap<String, Button> buttonMap = new HashMap<>();
    private final String[] pattern;
    private final Inventory inv;

    /**
     * 构造函数
     *
     * @param title   生成的Inventory的标题
     * @param pattern Inventory的内容规范
     */
    public InvSetter(String title, String... pattern) {
        if (pattern.length > 6) {
            throw new IllegalArgumentException("超出GUI最大限度");
        }
        for (String line : pattern) {
            if (line.length() > 9) {
                throw new IllegalArgumentException("超出单行最大格子数");
            }
        }
        this.pattern = pattern;
        inv = Bukkit.createInventory(null, pattern.length * 9, title);
    }

    /**
     * 设定构造函数pattern中，某项符号所代表的ItemStack
     *
     * @param sign 符号
     * @param item 符号所代表的ItemStack
     * @return 返回自身，创建链式代码
     */
    @NotNull
    public InvSetter setSign(String sign, ItemStack item) {
        signMap.put(sign, item);
        return this;
    }

    /**
     * 设定构造函数pattern中，某项符号所代表的ItemStack
     *
     * @param sign     符号
     * @param material 符号所代表的ItemStack (用Material构造ItemStack)
     * @return 返回自身，创建链式代码
     */
    @NotNull
    public InvSetter setSign(String sign, Material material) {
        return setSign(sign, new ItemStack(material));
        //
    }

    /**
     * 设定构造函数pattern中，某项符号所代表的Button
     *
     * @param sign   符号
     * @param button 符号所代表的Button
     * @return 返回自身，创建链式代码
     */
    @NotNull
    public InvSetter setSign(String sign, Button button) {
        buttonMap.put(sign, button);
        return setSign(sign, button.getStack());
    }

    /**
     * 以此InvSetter构造Inventory
     *
     * @return 构造后的Inventory
     */
    public Inventory getInv() {
        int slot;
        for (int i = 0; i < pattern.length; i++) {
            String line = pattern[i];
            slot = i * 9;
            for (String sign : line.split("")) {
                if (!signMap.containsKey(sign)) {
                    throw new IllegalStateException("存在未定义的符号: " + sign);
                }
                inv.setItem(slot, signMap.get(sign));
                if (buttonMap.containsKey(sign)) {
                    buttonMap.get(sign).getMenu().setButton(this, slot, buttonMap.get(sign));
                }
                slot++;
            }
        }
        return inv;
    }
}
