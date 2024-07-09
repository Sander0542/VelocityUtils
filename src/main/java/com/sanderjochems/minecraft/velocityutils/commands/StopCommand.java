package com.sanderjochems.minecraft.velocityutils.commands;

import com.sanderjochems.minecraft.velocityutils.commands.common.BaseCommand;
import com.sanderjochems.minecraft.velocityutils.utils.ChatUtil;
import com.velocitypowered.api.proxy.ProxyServer;

public class StopCommand extends BaseCommand {
    public StopCommand(ProxyServer server) {
        super(server);
    }

    @Override
    public void execute(Invocation invocation) {
        if (invocation.arguments().length > 0) {
            this.server.shutdown(ChatUtil.createComponent(String.join(" ", invocation.arguments())));
        } else {
            this.server.shutdown();
        }
    }

    @Override
    protected String getPermission() {
        return "velocityutils.stop";
    }

    @Override
    public String getCommand() {
        return "vstop";
    }

    @Override
    public String getDescription() {
        return "Stop the proxy server";
    }
}
