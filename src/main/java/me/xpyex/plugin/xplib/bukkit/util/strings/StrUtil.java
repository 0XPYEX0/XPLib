package me.xpyex.plugin.xplib.bukkit.util.strings;

public class StrUtil {
    public static String getStrBetweenKeywords(String str, String key1, String key2) {
        if (str == null || key1 == null || key2 == null) return "";

        int firstKeyIndex = key1.length() + str.indexOf(key1);
        return str.substring(firstKeyIndex, str.substring(firstKeyIndex).contains(key2) ? firstKeyIndex + str.substring(firstKeyIndex).indexOf(key2) : str.length());
    }

    public static boolean startsWithIgnoreCaseOr(String str, String... keys) {
        if (str == null || keys == null || keys.length == 0) return false;

        for (String s : keys) {
            if (str.toLowerCase().startsWith(s.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public static boolean endsWithIgnoreCaseOr(String str, String... ends) {
        if (str == null || ends == null || ends.length == 0) return false;

        for (String end : ends) {
            if (str.toLowerCase().endsWith(end.toLowerCase()))
                return true;
        }
        return false;
    }

    public static boolean containsIgnoreCaseOr(String str, String... keys) {
        if (str == null || keys == null)  return false;

        for (String key : keys) {
            if (str.toLowerCase().contains(key.toLowerCase()))
                return true;
        }
        return false;
    }

    public static boolean equalsIgnoreCaseOr(String target, String... contents) {
        if (target == null || contents == null) return false;

        if (contents.length == 0) return false;

        for (String s : contents) {
            if (s.equalsIgnoreCase(target))
                return true;
        }
        return false;
    }
}
