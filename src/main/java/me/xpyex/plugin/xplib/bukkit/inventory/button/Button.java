package me.xpyex.plugin.xplib.bukkit.inventory.button;

import java.util.HashMap;
import me.xpyex.plugin.xplib.bukkit.inventory.Menu;
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

    @Deprecated
    public final ItemStack getButton() {
        return getStack();
        //
    }

    /**
     * 获取该Button实例在当前状态对应的ItemStack
     * @return 该Button实例在当前状态对应的ItemStack
     */
    public final ItemStack getStack() {
        int i = condition.apply(menu.getPlayer());
        if (!MODES.containsKey(i)) {
            throw new IllegalStateException("按钮不存在该状态: " + i);
        }
        return MODES.get(i);
    }

    /**
     * 设定该Button在点击后会执行什么
     * @param effect 待执行的方法体
     * @return 返回自身，制造链式代码
     */
    public Button setClickEffect(ButtonClickEffect effect) {
        this.clickEffect = effect;
        return this;
        //
    }

    /**
     * 获取该Button在点击后会执行的方法体，或暂未被设定
     * @return 方法体
     */
    @Nullable
    public ButtonClickEffect getClickEffect() {
        return clickEffect;
        //
    }
}
