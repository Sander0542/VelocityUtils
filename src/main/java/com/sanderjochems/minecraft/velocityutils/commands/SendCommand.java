package com.sanderjochems.minecraft.velocityutils.commands;

import com.sanderjochems.minecraft.velocityutils.utils.ChatUtil;
import com.sanderjochems.minecraft.velocityutils.utils.SuggestionUtil;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.command.CommandMeta;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.ServerConnection;
import com.velocitypowered.api.proxy.server.RegisteredServer;

import java.util.*;

public class SendCommand implements VelocityUtilsCommand {

    private final ProxyServer server;

    public SendCommand(ProxyServer server) {
        this.server = server;
    }

    @Override
    public void execute(Invocation invocation) {
        CommandSource source = invocation.source();
        String[] arguments = invocation.arguments();

        if (arguments.length < 2) {
            ChatUtil.sendMessage(source, String.format("&cUsage: /%s <from> <to>", invocation.alias()));
            return;
        }

        final String fromArg = arguments[0];
        final String toArg = arguments[1];

        String toName = toArg.startsWith("#") ? toArg.substring(1) : toArg;

        Optional<RegisteredServer> toServer = this.parseToLocation(toArg);

        if (!toServer.isPresent()) {
            ChatUtil.sendMessage(source, String.format("&c%s is not a valid to location", toName));
            return;
        }

        List<Player> selectedPlayer = this.parseFromLocation(fromArg);

        String toServerName = toServer.get().getServerInfo().getName();
        ChatUtil.sendMessage(source, String.format("&2Attempting to send %d player%s to the server %s", selectedPlayer.size(), selectedPlayer.size() == 1 ? "" : "s", toServerName));

        for (Player player : selectedPlayer) {
            Optional<ServerConnection> currentServer = player.getCurrentServer();
            if (currentServer.isPresent() && currentServer.get().getServer() != toServer.get()) {
                player.createConnectionRequest(toServer.get()).fireAndForget();
            }
        }
    }

    @Override
    public boolean hasPermission(Invocation invocation) {
        return invocation.source().hasPermission("velocityutils.send");
    }

    @Override
    public List<String> suggest(Invocation invocation) {
        String[] arguments = invocation.arguments();

        if (arguments.length <= 1) {
            return this.getSuggestions(arguments.length > 0 ? arguments[0] : "", true);
        }
        if (arguments.length == 2) {
            return this.getSuggestions(arguments[1], false);
        }

        return Collections.emptyList();
    }

    private List<Player> parseFromLocation(String location) {
        if (location.equals("@a")) {
            return new ArrayList<>(this.server.getAllPlayers());
        } else if (location.startsWith("#")) {
            RegisteredServer server = this.getServer(location.substring(1));

            if (server != null) {
                return new ArrayList<>(server.getPlayersConnected());
            }
        } else {
            Player player = this.getPlayer(location);

            if (player != null) {
                return Collections.singletonList(player);
            }
        }

        return Collections.emptyList();
    }

    private Optional<RegisteredServer> parseToLocation(String location) {
        if (location.startsWith("#")) {
            RegisteredServer server = this.getServer(location.substring(1));

            if (server != null) {
                return Optional.of(server);
            }
        } else {
            Player player = this.getPlayer(location);

            if (player != null) {
                Optional<ServerConnection> server = player.getCurrentServer();
                if (server.isPresent()) {
                    return Optional.of(server.get().getServer());
                }
            }
        }

        return Optional.empty();
    }

    private RegisteredServer getServer(String name) {
        return this.server.getServer(name).orElse(null);
    }

    private Player getPlayer(String username) {
        return this.server.getPlayer(username).orElse(null);
    }

    private List<String> getSuggestions(String argument, boolean showAll) {
        List<String> suggestions = new ArrayList<>();

        if (showAll) {
            suggestions.add("@a");
        }
        suggestions.addAll(SuggestionUtil.getServers(this.server));
        suggestions.addAll(SuggestionUtil.getPlayers(this.server));

        return SuggestionUtil.filterSuggestions(argument, suggestions);
    }

    @Override
    public CommandMeta getMeta(CommandManager commandManager) {
        return commandManager.metaBuilder("send").build();
    }
}
