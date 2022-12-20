package me.xpyex.plugin.xplib.bukkit.inventory;

import me.xpyex.plugin.xplib.bukkit.inventory.button.Button;
import me.xpyex.plugin.xplib.bukkit.inventory.button.ButtonClickEffect;
import me.xpyex.plugin.xplib.bukkit.inventory.button.ButtonReturnItem;
import me.xpyex.plugin.xplib.bukkit.inventory.button.ModifiableButton;
import me.xpyex.plugin.xplib.bukkit.inventory.button.UnmodifiableButton;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class HandleMenu implements Listener {
    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player whoClicked = (Player) event.getWhoClicked();
        Menu menu = Menu.getOpeningMenu(whoClicked);
        if (menu == null) {
            return;
        }
        if (event.getClickedInventory() == null) {
            event.setCancelled(true);
            return;
        }
        if (event.getClickedInventory() == event.getWhoClicked().getInventory()) {
            if (event.isShiftClick()) {
                event.setCancelled(true);  //不允许用Shift把东西放入菜单
            }
            return;
        }
        Button button = menu.getButton(event.getSlot());
        if (button == null) {
            event.setCancelled(true);
            return;
        }
        ButtonClickEffect clickEffect = button.getClickEffect();
        if (clickEffect != null) {
            clickEffect.click(whoClicked, event.getClick(), event.getCurrentItem());
        }
        if (button instanceof UnmodifiableButton) {
            event.setCancelled(true);
        } else if (button instanceof ModifiableButton) {
            ButtonReturnItem returnItemEffect = ((ModifiableButton) button).getReturnItem();
            if (returnItemEffect != null) {
                event.getClickedInventory().setItem(event.getSlot(), returnItemEffect.returnItem(whoClicked, event.getClick(), event.getCurrentItem()));
            }
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        Menu.closed((Player) event.getPlayer());
        //
    }
}
