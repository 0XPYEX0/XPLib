package me.xpyex.plugin.xplib.bukkit.inventory.button;

import me.xpyex.plugin.xplib.bukkit.inventory.Menu;
import me.xpyex.plugin.xplib.bukkit.util.value.ValueUtil;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

/**
 * 可被修改的按钮格
 */
public class ModifiableButton extends Button {
    private ButtonReturnItem returnItemEffect;
    private ItemStack stack = new ItemStack(Material.AIR);

    public ModifiableButton(Menu menu, ButtonCondition condition) {
        super(menu, condition);
        //
    }

    @Nullable
    public ButtonReturnItem getReturnItem() {
        return returnItemEffect;
        //
    }

    public void setReturnItem(ButtonReturnItem f) {
        this.returnItemEffect = f;
        //
    }

    @Override
    public ItemStack getStack() {
        return stack;
        //
    }

    public void setStack(ItemStack stack) {
        ValueUtil.checkNull("stack不应为null", stack);
        this.stack = stack;
    }
}
