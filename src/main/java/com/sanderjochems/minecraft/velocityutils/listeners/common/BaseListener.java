package com.sanderjochems.minecraft.velocityutils.listeners.common;

import com.velocitypowered.api.proxy.ProxyServer;

public abstract class BaseListener {

    protected final ProxyServer server;

    public BaseListener(ProxyServer server) {
        this.server = server;
    }
}
