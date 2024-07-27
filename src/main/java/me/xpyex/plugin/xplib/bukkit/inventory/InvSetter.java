package me.xpyex.plugin.xplib.bukkit.inventory;

/**
 * @deprecated 已过时
 * 请使用 {@link InvBuilder}
 */
@Deprecated
public class InvSetter extends InvBuilder {
    /**
     * 构造函数
     *
     * @param title   生成的Inventory的标题
     * @param pattern Inventory的内容规范
     */
    public InvSetter(String title, String... pattern) {
        super(title, pattern);
        //
    }
}
