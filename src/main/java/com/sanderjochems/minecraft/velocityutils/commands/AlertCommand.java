package com.sanderjochems.minecraft.velocityutils.commands;

import com.sanderjochems.minecraft.velocityutils.utils.ChatUtil;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.command.CommandMeta;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;

public class AlertCommand extends VelocityUtilsCommand {

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
    protected CommandMeta getMeta(CommandManager commandManager) {
        return commandManager.metaBuilder("alert").build();
    }
}
