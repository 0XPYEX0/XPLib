package me.xpyex.plugin.xplib.bukkit.inventory;

import java.util.ArrayList;
import java.util.HashMap;
import me.xpyex.plugin.xplib.bukkit.api.Pair;
import me.xpyex.plugin.xplib.bukkit.inventory.button.Button;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Menu {
    private static final HashMap<String, Menu> MENUS = new HashMap<>();
    private final HashMap<Pair<Integer, Integer>, Button> buttons = new HashMap<>();
    private final Player player;
    private final HashMap<Integer, InvSetter> setters = new HashMap<>();
    private final HashMap<Integer, Inventory> pages = new HashMap<>();
    private int openingPage;

    public Menu(Player player) {
        this.player = player;
        //
    }

    public Menu setPage(int page, InvSetter setter) {
        this.setters.put(page, setter);
        return this;
    }

    public Menu setPage(int page, String title, int size) {
        ArrayList<String> pattern = new ArrayList<>();
        {
            for (int i = 0; i < size; i++) {
                pattern.add("         ");
            }
        }
        setters.put(page, new InvSetter(title, pattern.toArray(new String[0])).setSign(" ", Material.AIR));
        return this;
    }

    public Menu setSign(String sign, ItemStack stack, int page) {
        setters.get(page).setSign(sign, stack);
        return this;
    }

    public Menu setSign(String sign, Button button, int page) {
        setters.get(page).setSign(sign, button);
        return this;
    }

    public Player getPlayer() {
        return player;
        //
    }

    public Menu setButton(int page, int slot, Button button) {
        if (button != null) {
            buttons.put(new Pair<>(page, slot), button);
        }
        return this;
    }

    public Menu setButton(InvSetter setter, int slot, Button button) {
        if (setter != null && button != null) {
            for (Integer k : setters.keySet()) {
                if (setters.get(k) == setter) {
                    setButton(k, slot, button);
                    break;
                }
            }
        }
        return this;
    }

    public void updateInventory() {
        buttons.forEach((pair, button) -> {
            if (pair.getKey() == getOpeningPage()) {
                pages.get(getOpeningPage()).setItem(pair.getValue(), button.getButton());
            }
        });
    }

    public int getOpeningPage() {
        return openingPage;
    }

    public void open(int page) {
        Inventory out = setters.get(page).getInv();
        player.openInventory(out);
        pages.put(page, out);
        updateInventory();
        this.openingPage = page;
        MENUS.put(player.getUniqueId().toString(), this);
    }

    public static Menu getOpeningMenu(Player player) {
        return MENUS.get(player.getUniqueId().toString());
        //
    }

    public static void closed(Player player) {
        MENUS.remove(player.getUniqueId().toString());
        //
    }

    public static HashMap<String, Menu> getMenus() {
        return MENUS;
        //
    }

    public Button getButton(int slot) {
        Pair<Integer, Integer> pair = new Pair<>(getOpeningPage(), slot);
        for (Pair<Integer, Integer> i : buttons.keySet()) {
            if (i.equals(pair)) {
                return buttons.get(i);
            }
        }
        return null;
    }
}
