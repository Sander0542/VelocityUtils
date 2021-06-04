package com.sanderjochems.minecraft.velocityutils.commands;

import com.sanderjochems.minecraft.velocityutils.commands.common.PlayerCommand;
import com.sanderjochems.minecraft.velocityutils.utils.ChatUtil;
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
    public String getCommand() {
        return "ip";
    }

    @Override
    public String getDescription() {
        return "Show the ip address of a given player";
    }
}
