package me.xpyex.plugin.xplib.bukkit.api;

import java.util.HashMap;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class InvSetter {
    private final HashMap<String, ItemStack> signMap = new HashMap<>();
    private final String[] pattern;
    private final Inventory inv;

    public InvSetter(InventoryHolder who, String title, String... pattern) {
        if (pattern.length > 6) {
            throw new IllegalArgumentException("超出GUI最大限度");
        }
        for (String line : pattern) {
            if (line.length() > 9) {
                throw new IllegalArgumentException("超出单行最大格子数");
            }
        }
        this.pattern = pattern;
        inv = Bukkit.createInventory(who, pattern.length * 9, title);
    }

    public void setSign(String sign, ItemStack item) {
        signMap.put(sign, item);
        //
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
                slot++;
            }
        }
        return inv;
    }
}
