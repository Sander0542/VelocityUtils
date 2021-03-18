package com.sanderjochems.minecraft.velocityutils.commands;

import com.sanderjochems.minecraft.velocityutils.utils.ChatUtil;
import com.sanderjochems.minecraft.velocityutils.utils.PlayerUtil;
import com.sanderjochems.minecraft.velocityutils.utils.SuggestionUtil;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.command.CommandMeta;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.ServerConnection;

import java.util.List;
import java.util.Optional;

public class JoinCommand extends PlayerCommand {

    public JoinCommand(ProxyServer server) {
        super(server);
    }

    @Override
    public void execute(Invocation invocation, Player player) {
        if (!(invocation.source() instanceof Player)) {
            ChatUtil.sendMessage(invocation.source(), "&cOnly players can use this command");
        }

        Optional<ServerConnection> server = player.getCurrentServer();

        if (!server.isPresent()) {
            ChatUtil.sendMessage(invocation.source(), String.format("&c%s is online, but not on a server at this time", player.getUsername()));
            return;
        }

        Player executor = (Player) invocation.source();

        if (PlayerUtil.inServer(executor, server.get())) {
            ChatUtil.sendMessage(invocation.source(), String.format("&cYou are in the same server as %s", player.getUsername()));
            return;
        }

        executor.createConnectionRequest(server.get().getServer()).fireAndForget();
    }

    @Override
    protected String getPermission() {
        return "velocityutils.join";
    }

    @Override
    public CommandMeta getMeta(CommandManager commandManager) {
        return commandManager.metaBuilder("join").build();
    }
}
