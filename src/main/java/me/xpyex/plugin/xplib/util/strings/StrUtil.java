package me.xpyex.plugin.xplib.util.strings;

import me.xpyex.plugin.xplib.util.RootUtil;
import me.xpyex.plugin.xplib.util.value.ValueUtil;

public class StrUtil extends RootUtil {
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
        if (str == null || keys == null) return false;

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
        if (str == null || ends == null) return false;

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
        if (str == null || keys == null) return false;

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
        if (target == null || contents == null) return false;

        for (String s : contents) {
            if (s.equalsIgnoreCase(target))
                return true;
        }
        return false;
    }

    /**
     * 替换关键词，同时忽略大小写，需要计算
     *
     * @param target      需要替换的String
     * @param replacement 用什么替换关键词
     * @param keyWord     需要替换的关键词
     * @return 替换后的String
     */
    public static String replaceKeyWordIgnoreCase(String target, String replacement, String... keyWord) {
        ValueUtil.notNull("需要替换的String为null", target);
        ValueUtil.notNull("替换String为null", replacement);
        if (keyWord == null || keyWord.length == 0) return target;

        String result = target;
        for (String s : keyWord) {
            while (containsIgnoreCaseOr(result, s)) {  //假设需要替换所有Player，文本中可能第一个是player，第二个是PLayer等极端情况，所以使用while
                int startIndex = result.toLowerCase().indexOf(s.toLowerCase());
                int endIndex = startIndex + s.length();
                result = result.replace(result.substring(startIndex, endIndex), replacement);
            }
        }
        return result;
    }
}
