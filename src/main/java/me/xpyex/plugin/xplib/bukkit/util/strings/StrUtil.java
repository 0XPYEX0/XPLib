package me.xpyex.plugin.xplib.bukkit.util.strings;

public class StrUtil {
    /**
     * 获取两个关键词中间的字符串
     *
     * @param str  原文本
     * @param key1 关键词1
     * @param key2 关键词2
     * @return 两个关键词中间的字符串
     */
    public static String getStrBetweenKeywords(String str, String key1, String key2) {
        if (str == null || key1 == null || key2 == null) return "";

        int firstKeyIndex = key1.length() + str.indexOf(key1);
        return str.substring(firstKeyIndex, str.substring(firstKeyIndex).contains(key2) ? firstKeyIndex + str.substring(firstKeyIndex).indexOf(key2) : str.length());
    }

    /**
     * 检查文本是否以关键词之一开头，忽略大小写
     *
     * @param str  原文本
     * @param keys 关键词
     * @return str是否以keys的其中之一开头
     */
    public static boolean startsWithIgnoreCaseOr(String str, String... keys) {
        if (str == null || keys == null || keys.length == 0) return false;

        for (String s : keys) {
            if (str.toLowerCase().startsWith(s.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 检查文本是否以关键词之一结尾，忽略大小写
     *
     * @param str  原文本
     * @param ends 关键词
     * @return str是否以ends的其中之一结尾
     */
    public static boolean endsWithIgnoreCaseOr(String str, String... ends) {
        if (str == null || ends == null || ends.length == 0) return false;

        for (String end : ends) {
            if (str.toLowerCase().endsWith(end.toLowerCase()))
                return true;
        }
        return false;
    }

    /**
     * 检查文本是否包含关键词之一，忽略大小写
     *
     * @param str  原文本
     * @param keys 关键词
     * @return str是否包含关键词其中之一
     */
    public static boolean containsIgnoreCaseOr(String str, String... keys) {
        if (str == null || keys == null || keys.length == 0) return false;

        for (String key : keys) {
            if (str.toLowerCase().contains(key.toLowerCase()))
                return true;
        }
        return false;
    }

    /**
     * 检查文本是否与关键词之一相等，忽略大小写
     *
     * @param target   原文本
     * @param contents 比较文本
     * @return target是否与contents之一相等
     */
    public static boolean equalsIgnoreCaseOr(String target, String... contents) {
        if (target == null || contents == null || contents.length == 0) return false;

        for (String s : contents) {
            if (s.equalsIgnoreCase(target))
                return true;
        }
        return false;
    }
}
