package com.sanderjochems.minecraft.velocityutils.commands;

import com.sanderjochems.minecraft.velocityutils.utils.ChatUtil;
import com.sanderjochems.minecraft.velocityutils.utils.SuggestionUtil;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.RegisteredServer;

import java.util.List;
import java.util.Optional;

public abstract class ServerCommand extends VelocityUtilsCommand {
    public ServerCommand(ProxyServer server) {
        super(server);
    }

    abstract void execute(Invocation invocation, RegisteredServer server);

    @Override
    public void execute(Invocation invocation) {
        CommandSource source = invocation.source();
        String[] arguments = invocation.arguments();

        if (arguments.length < 1) {
            ChatUtil.sendMessage(source, String.format("&cUsage: /%s <server>", invocation.alias()));
            return;
        }

        String serverName = arguments[0];

        Optional<RegisteredServer> server = this.server.getServer(serverName);

        if (!server.isPresent()) {
            ChatUtil.sendMessage(source, String.format("&c%s does not exist", serverName));
            return;
        }

        this.execute(invocation, server.get());
    }

    @Override
    public List<String> suggest(Invocation invocation) {
        String[] arguments = invocation.arguments();
        if (arguments.length > 1) {
            return super.suggest(invocation);
        }

        String serverName = arguments.length > 0 ? arguments[0] : "";

        return SuggestionUtil.filterSuggestions(serverName, SuggestionUtil.getServers(this.server));
    }
}
