package com.sanderjochems.minecraft.velocityutils.commands;

import com.sanderjochems.minecraft.velocityutils.utils.ChatUtil;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.command.CommandMeta;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;

public class IpCommand extends PlayerCommand {

    public IpCommand(ProxyServer server) {
        super(server);
    }

    @Override
    public void execute(SimpleCommand.Invocation invocation, Player player) {
        ChatUtil.sendMessage(invocation.source(), String.format("&aIP of %s is %s", player.getUsername(), player.getRemoteAddress().toString()));
    }

    @Override
    protected String getPermission() {
        return "velocityutils.ip";
    }

    @Override
    public CommandMeta getMeta(CommandManager commandManager) {
        return commandManager.metaBuilder("ip").build();
    }
}
