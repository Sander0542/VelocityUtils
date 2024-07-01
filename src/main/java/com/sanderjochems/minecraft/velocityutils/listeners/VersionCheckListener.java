package com.sanderjochems.minecraft.velocityutils.listeners;

import com.sanderjochems.minecraft.velocityutils.VelocityUtils;
import com.sanderjochems.minecraft.velocityutils.listeners.common.BaseListener;
import com.sanderjochems.minecraft.velocityutils.utils.ChatUtil;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.PostLoginEvent;
import com.velocitypowered.api.proxy.ProxyServer;

public class VersionCheckListener extends BaseListener {
    public VersionCheckListener(ProxyServer server) {
        super(server);
    }

    @Subscribe
    public void onPostLogin(PostLoginEvent event) {
        if (event.getPlayer().hasPermission("velocityutils.version") && VelocityUtils.getInstance().isNewerVersionAvailable()) {
            ChatUtil.sendVersionCheck(event.getPlayer());
        }
    }
}
