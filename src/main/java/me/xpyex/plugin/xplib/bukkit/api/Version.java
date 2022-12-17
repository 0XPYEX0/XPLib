package me.xpyex.plugin.xplib.bukkit.api;

import java.math.BigInteger;
import java.util.TreeSet;
import me.xpyex.plugin.xplib.bukkit.util.strings.StrUtil;

public class Version {
    private final int[] versions;
    private final String betaInfo;

    public Version(String ver) {
        String mainVer;
        if (ver.contains("-")) {
            mainVer = ver.split("-")[0];
            betaInfo = ver.substring((mainVer + "-").length());
        } else {
            mainVer = ver;
            betaInfo = "";
        }

        if (StrUtil.containsIgnoreCaseOr(mainVer, "version", "ver", "v")) {
            mainVer = mainVer.replace("version", "");
            mainVer = mainVer.replace("ver", "");
            mainVer = mainVer.replace("v", "");
        }

        String[] vers = mainVer.split("\\.");
        versions = new int[vers.length];
        for (int i = 0; i < vers.length; i++) {
            versions[i] = Integer.parseInt(vers[i]);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof Version) {
            Version v = (Version) o;
            for (int i = 0; i < versions.length; i++) {
                if (v.versions[i] != this.versions[i]) {
                    return false;
                }
            }  //主版本是否一致
            return v.betaInfo.equals(this.betaInfo);  //Beta版本是否一致
        }
        return false;
    }

    /**
     * 比较两个Version
     * @param version 目标Version
     * @return 返回0相等，返回1表示this较新于version，返回-1表示this较旧于version
     */
    public int compareTo(Version version) {
        if (version == null) return -1;

        if (version.versions.length > this.versions.length) {
            return -1;
        }
        if (version.versions.length < this.versions.length) {  //1.19.3比1.19新
            return 1;
        }

        for (int i = 0; i < versions.length; i++) {
            if (version.versions[i] > this.versions[i]) {
                return 1;
            }
            if (version.versions[i] == this.versions[i]) {
                if (version.betaInfo.equals(this.betaInfo)) {  //Beta版本是否一致
                    return 0;
                }
                TreeSet<String> compare = new TreeSet<>();
                compare.add(version.betaInfo);
                compare.add(this.betaInfo);
                if (compare.first().equals(this.betaInfo)) {  //this较新
                    return 1;
                } else {
                    return -1;
                }
            }
            if (version.versions[i] < this.versions[i]) {
                return -1;
            }
        }

        return 0;
    }
}
