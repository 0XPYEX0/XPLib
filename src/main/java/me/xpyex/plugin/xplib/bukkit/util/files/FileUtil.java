package me.xpyex.plugin.xplib.bukkit.util.files;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import me.xpyex.plugin.xplib.bukkit.util.RootUtil;
import me.xpyex.plugin.xplib.bukkit.util.value.ValueUtil;

public class FileUtil extends RootUtil {
    /**
     * 创建文件
     *
     * @param target   目标文件
     * @param replaced 如果所在位置已存在文件，是否覆盖
     * @throws IOException 文件异常
     */
    public static void createNewFile(File target, boolean replaced) throws IOException {
        ValueUtil.notNull("File不应为null", target);
        if (replaced && target.exists()) {
            target.delete();
        }
        target.getParentFile().mkdirs();
        target.createNewFile();
    }

    /**
     * 读取目标文本文件
     *
     * @param target 目标文本文件
     * @return 目标文件的文本
     * @throws IOException 文件异常
     */
    public static String readFile(File target) throws IOException {
        List<String> content = Files.readAllLines(target.toPath(), StandardCharsets.UTF_8);
        if (content.isEmpty()) {
            return "";
        }
        return String.join("\n", content);
    }

    /**
     * 向目标文件写出文本
     *
     * @param target  目标文本
     * @param content 要写出的内容
     * @param append  是否在原文本的内容基础续写新文本，否则覆写整个文件
     * @throws IOException 文件异常
     */
    public static void writeFile(File target, String content, boolean append) throws IOException {
        if (!target.exists()) {
            createNewFile(target, false);
        }
        try (BufferedWriter out = Files.newBufferedWriter(target.toPath(), StandardCharsets.UTF_8)) {
            if (append) {
                out.append(content);
            } else {
                out.write(content);
            }
        }
    }

    /**
     * 覆写目标文件
     *
     * @param target  目标文件
     * @param content 覆写的内容
     * @throws IOException 文件异常
     */
    public static void writeFile(File target, String content) throws IOException {
        writeFile(target, content, false);
    }
}
