package com.sanderjochems.minecraft.velocityutils.tasks.common;

import com.velocitypowered.api.proxy.ProxyServer;

public abstract class BaseTask implements Runnable {

    protected final ProxyServer server;

    public BaseTask(ProxyServer server) {
        this.server = server;
    }
}
