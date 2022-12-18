package me.xpyex.plugin.xplib.bukkit.inventory;

import java.util.ArrayList;
import java.util.HashMap;
import me.xpyex.plugin.xplib.bukkit.inventory.button.Button;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Menu {
    private static final HashMap<String, Menu> MENUS = new HashMap<>();
    private final HashMap<Integer, Button> BUTTONS = new HashMap<>();
    private final Player player;
    private final InvSetter setter;
    private Inventory toOpen;

    public Menu(Player player, String title, int size) {
        this.player = player;
        ArrayList<String> pattern = new ArrayList<>();
        {
            for (int i = 0; i < size; i++) {
                pattern.add("         ");
            }
        }
        setter = new InvSetter(player, title, pattern.toArray(new String[0]));
        setter.setSign(" ", Material.AIR);
    }

    public Menu(Player player, InvSetter setter) {
        this.player = player;
        this.setter = setter;
    }

    public Menu setSign(String sign, ItemStack stack) {
        setter.setSign(sign, stack);
        return this;
    }

    public Menu setSign(String sign, Button button) {
        setter.setSign(sign, button);
        return this;
    }

    public Player getPlayer() {
        return player;
    }

    public Menu setButton(int slot, Button button) {
        if (button != null) {
            BUTTONS.put(slot, button);
        }
        return this;
    }

    public void updateInventory() {
        if (toOpen == null) {
            return;
        }
        BUTTONS.forEach((slot, button) -> {
            toOpen.setItem(slot, button.getButton());
        });
    }

    public void open() {
        player.openInventory(toOpen = setter.getInv());
        updateInventory();
        MENUS.put(player.getUniqueId().toString(), this);
    }

    public static Menu getOpeningMenu(Player player) {
        return MENUS.get(player.getUniqueId().toString());
        //
    }

    public static void close(Player player) {
        player.closeInventory();
        MENUS.remove(player.getUniqueId().toString());
    }

    public static HashMap<String, Menu> getMenus() {
        return MENUS;
        //
    }

    public Button getButton(int slot) {
        return BUTTONS.get(slot);
        //
    }
}
