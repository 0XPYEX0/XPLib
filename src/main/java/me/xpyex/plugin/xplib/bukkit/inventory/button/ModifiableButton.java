package me.xpyex.plugin.xplib.bukkit.inventory.button;

import me.xpyex.plugin.xplib.bukkit.inventory.Menu;
import org.jetbrains.annotations.Nullable;

/**
 * 可被修改的按钮格
 */
public class ModifiableButton extends Button {
    private ButtonReturnItem returnItemEffect;

    public ModifiableButton(Menu menu, ButtonCondition condition) {
        super(menu, condition);
        //
    }

    public void setReturnItem(ButtonReturnItem f) {
        this.returnItemEffect = f;
        //
    }

    @Nullable
    public ButtonReturnItem getReturnItem() {
        return returnItemEffect;
        //
    }
}
