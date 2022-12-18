package me.xpyex.plugin.xplib.bukkit.inventory.button;

import java.util.HashMap;
import me.xpyex.plugin.xplib.bukkit.inventory.Menu;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

public abstract class Button {
    private final HashMap<Integer, ItemStack> MODES = new HashMap<>();  //各种状态是什么按钮
    private final ButtonCondition condition;
    private final Menu menu;
    private ButtonClickEffect clickEffect;

    public Button(Menu menu, ButtonCondition condition) {
        this.condition = condition;
        this.menu = menu;
        //
    }

    public Menu getMenu() {
        return menu;
        //
    }

    public final Button addMode(int mode, ItemStack button) {
        MODES.put(mode, button);
        return this;
        //
    }

    public final ItemStack getButton() {
        int i = condition.apply(menu.getPlayer(), ClickType.UNKNOWN);
        if (!MODES.containsKey(i)) {
            throw new IllegalStateException("按钮不存在该状态: " + i);
        }
        return MODES.get(i);
    }

    public Button setClickEffect(ButtonClickEffect effect) {
        this.clickEffect = effect;
        return this;
        //
    }

    @Nullable
    public ButtonClickEffect getClickEffect() {
        return clickEffect;
        //
    }
}
