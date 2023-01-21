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
import org.bukkit.inventory.ItemStack;

public class HandleMenu implements Listener {
    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;

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
                ItemStack item = returnItemEffect.returnItem(whoClicked, event.getClick(), event.getCurrentItem());
                ((ModifiableButton) button).setStack(item);
                event.getClickedInventory().setItem(event.getSlot(), item);
            } else {
                ((ModifiableButton) button).setStack(event.getCurrentItem());
            }
        }
        menu.updateInventory();  //实时更新
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        Menu.closed((Player) event.getPlayer());
        //
    }
}
