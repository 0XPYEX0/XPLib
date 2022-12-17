package me.xpyex.plugin.xplib.bukkit.util.version;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.ByteArrayOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import me.xpyex.plugin.xplib.bukkit.XPLib;
import me.xpyex.plugin.xplib.bukkit.api.Version;
import me.xpyex.plugin.xplib.bukkit.util.config.GsonUtil;
import org.bukkit.plugin.Plugin;

public class UpdateUtil {
    public static String getUpdateFromGitee(Plugin plugin) {
        try {
            HttpURLConnection huc = (HttpURLConnection) new URL("https://gitee.com/api/v5/repos/xpyex/" + plugin.getName() + "/tags").openConnection();
            huc.setRequestMethod("GET");
            huc.connect();
            ByteArrayOutputStream ba = new ByteArrayOutputStream(16384);
            int nRead;
            byte[] data = new byte[4096];
            while ((nRead = huc.getInputStream().read(data, 0, data.length)) != -1) {
                ba.write(data, 0, nRead);
            }
            JsonArray array = GsonUtil.parseJsonArray(ba.toString("UTF-8"));
            JsonObject latestVer = array.get(0).getAsJsonObject();
            String name = latestVer.get("name").getAsString();
            if (new Version(name).compareTo(new Version(plugin.getDescription().getVersion())) > 0) {
                return name;
            }
        } catch (Throwable e) {
            XPLib.getInstance().getLogger().warning("插件 " + plugin.getName() + " 获取更新失败");
            e.printStackTrace();
        }
        return null;
    }
}
