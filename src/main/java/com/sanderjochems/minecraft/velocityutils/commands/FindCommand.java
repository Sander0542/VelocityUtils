package com.sanderjochems.minecraft.velocityutils.commands;

import com.sanderjochems.minecraft.velocityutils.utils.ChatUtil;
import com.sanderjochems.minecraft.velocityutils.utils.SuggestionUtil;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.command.CommandMeta;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.ServerConnection;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class FindCommand implements VelocityUtilsCommand {

    private final ProxyServer server;

    public FindCommand(ProxyServer server) {
        this.server = server;
    }

    @Override
    public void execute(Invocation invocation) {
        CommandSource source = invocation.source();
        String[] arguments = invocation.arguments();

        if (arguments.length < 1) {
            ChatUtil.sendMessage(source, String.format("&cUsage: /%s <player>", invocation.alias()));
        }

        String username = arguments[0];

        Optional<Player> player = this.server.getPlayer(username);

        if (!player.isPresent()) {
            ChatUtil.sendMessage(source, String.format("&c%s is not online", username));
        }

        Optional<ServerConnection> server = player.get().getCurrentServer();

        if (!server.isPresent()) {
            ChatUtil.sendMessage(source, String.format("&c%s is online, but not on a server at this time", username));
        }

        String serverName = server.get().getServer().getServerInfo().getName();

        ChatUtil.sendMessage(source, String.format("&a%s was found %s", username, serverName));
    }

    @Override
    public List<String> suggest(Invocation invocation) {
        String[] arguments = invocation.arguments();
        if (arguments.length > 1) {
            return Collections.emptyList();
        }

        String username = arguments.length > 0 ? arguments[0] : "";

        return SuggestionUtil.filterSuggestions(username, SuggestionUtil.getPlayers(this.server));
    }

    @Override
    public boolean hasPermission(Invocation invocation) {
        return invocation.source().hasPermission("velocityutils.find");
    }

    @Override
    public CommandMeta getMeta(CommandManager commandManager) {
        return commandManager.metaBuilder("find").build();
    }
}
