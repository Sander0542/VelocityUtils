package com.sanderjochems.minecraft.velocityutils.tasks;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sanderjochems.minecraft.velocityutils.Constants;
import com.sanderjochems.minecraft.velocityutils.VelocityUtils;
import com.sanderjochems.minecraft.velocityutils.tasks.common.BaseTask;
import com.sanderjochems.minecraft.velocityutils.utils.ChatUtil;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class VersionCheckerTask extends BaseTask {

    public static final String VersionUnknown = "Unknown";

    public VersionCheckerTask(ProxyServer server) {
        super(server);
    }

    @Override
    public void run() {
        String newestVersion = this.getNewestVersion();

        if (!newestVersion.equals(VersionUnknown)) {
            VelocityUtils.getInstance().setNewestVersion(newestVersion);

            if (VelocityUtils.getInstance().isNewerVersionAvailable()) {
                VelocityUtils.getLogger().info("Newer version available: {}", newestVersion);

                this.notifyAdmins();
            }
        }
    }

    private String getNewestVersion() {
        try {
            JsonObject jsonObject = this.getApiResponse();

            if (jsonObject.has("tag_name")) {
                return jsonObject.get("tag_name").getAsString().replaceFirst("v", "");
            }
        } catch (Exception ignored) {
        }

        return VersionUnknown;
    }

    private JsonObject getApiResponse() throws Exception {
        BufferedReader reader = null;
        try {
            URL url = new URL(Constants.VersionApi);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));

            StringBuilder buffer = new StringBuilder();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1) {
                buffer.append(chars, 0, read);
            }

            return new Gson().fromJson(buffer.toString(), JsonObject.class);
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

    private void notifyAdmins() {
        for (Player player : this.server.getAllPlayers()) {
            if (player.hasPermission("velocityutils.version")) {
                ChatUtil.sendVersionCheck(player);
            }
        }
    }
}
