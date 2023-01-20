package me.xpyex.plugin.xplib.bukkit.inventory;

import java.util.HashMap;
import me.xpyex.plugin.xplib.bukkit.inventory.button.Button;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InvSetter {
    private final HashMap<String, ItemStack> signMap = new HashMap<>();
    private final HashMap<String, Button> buttonMap = new HashMap<>();
    private final String[] pattern;
    private final Inventory inv;

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

    public InvSetter setSign(String sign, ItemStack item) {
        signMap.put(sign, item);
        return this;
    }

    public InvSetter setSign(String sign, Material material) {
        return setSign(sign, new ItemStack(material));
        //
    }

    public InvSetter setSign(String sign, Button button) {
        buttonMap.put(sign, button);
        return setSign(sign, button.getStack());
    }

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
