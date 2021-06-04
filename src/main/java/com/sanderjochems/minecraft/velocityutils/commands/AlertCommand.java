package com.sanderjochems.minecraft.velocityutils.commands;

import com.sanderjochems.minecraft.velocityutils.commands.common.Command;
import com.sanderjochems.minecraft.velocityutils.utils.ChatUtil;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;

public class AlertCommand extends Command {

    public AlertCommand(ProxyServer server) {
        super(server);
    }

    @Override
    public void execute(SimpleCommand.Invocation invocation) {
        if (invocation.arguments().length < 1) {
            ChatUtil.sendMessage(invocation.source(), String.format("&cUsage: /%s <message>", invocation.alias()));
            return;
        }

        for (Player player : this.server.getAllPlayers()) {
            ChatUtil.sendMessage(player, String.join(" ", invocation.arguments()));
        }
    }

    @Override
    protected String getPermission() {
        return "velocityutils.alert";
    }

    @Override
    public String getCommand() {
        return "alert";
    }

    @Override
    public String getDescription() {
        return "Send an alert to all the players on the server";
    }
}
