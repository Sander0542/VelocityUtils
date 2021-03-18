package com.sanderjochems.minecraft.velocityutils.commands;

import com.sanderjochems.minecraft.velocityutils.utils.ChatUtil;
import com.sanderjochems.minecraft.velocityutils.utils.SuggestionUtil;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.command.CommandMeta;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.ServerConnection;

import java.util.List;
import java.util.Optional;

public class FindCommand extends PlayerCommand {

    public FindCommand(ProxyServer server) {
        super(server);
    }

    @Override
    public void execute(SimpleCommand.Invocation invocation, Player player) {
        Optional<ServerConnection> server = player.getCurrentServer();

        if (!server.isPresent()) {
            ChatUtil.sendMessage(invocation.source(), String.format("&c%s is online, but not on a server at this time", player.getUsername()));
            return;
        }

        String serverName = server.get().getServer().getServerInfo().getName();

        ChatUtil.sendMessage(invocation.source(), String.format("&a%s was found %s", player.getUsername(), serverName));
    }

    @Override
    public String getPermission() {
        return "velocityutils.find";
    }

    @Override
    public CommandMeta getMeta(CommandManager commandManager) {
        return commandManager.metaBuilder("find").build();
    }
}
