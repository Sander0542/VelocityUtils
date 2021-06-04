package com.sanderjochems.minecraft.velocityutils.commands;

import com.sanderjochems.minecraft.velocityutils.commands.common.Command;
import com.sanderjochems.minecraft.velocityutils.utils.ChatUtil;
import com.sanderjochems.minecraft.velocityutils.utils.PlayerUtil;
import com.sanderjochems.minecraft.velocityutils.utils.SuggestionUtil;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.ServerConnection;
import com.velocitypowered.api.proxy.server.RegisteredServer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class SendCommand extends Command {

    public SendCommand(ProxyServer server) {
        super(server);
    }

    @Override
    public void execute(SimpleCommand.Invocation invocation) {
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
            if (!PlayerUtil.inServer(player, toServer.get())) {
                player.createConnectionRequest(toServer.get()).fireAndForget();
            }
        }
    }

    @Override
    protected String getPermission() {
        return "velocityutils.send";
    }

    @Override
    public String getCommand() {
        return "send";
    }

    @Override
    public String getDescription() {
        return "Send a given player or group to another server of player";
    }

    @Override
    public List<String> suggest(SimpleCommand.Invocation invocation) {
        String[] arguments = invocation.arguments();

        if (arguments.length <= 1) {
            return this.getSuggestions(arguments.length > 0 ? arguments[0] : "", true);
        }
        if (arguments.length == 2) {
            return this.getSuggestions(arguments[1], false);
        }

        return super.suggest(invocation);
    }

    private List<Player> parseFromLocation(String location) {
        if (location.equals("@a")) {
            return new ArrayList<>(this.server.getAllPlayers());
        } else if (location.startsWith("#")) {
            Optional<RegisteredServer> server = this.server.getServer(location.substring(1));

            if (server.isPresent()) {
                return new ArrayList<>(server.get().getPlayersConnected());
            }
        } else {
            Optional<Player> player = this.server.getPlayer(location);

            if (player.isPresent()) {
                return Collections.singletonList(player.get());
            }
        }

        return Collections.emptyList();
    }

    private Optional<RegisteredServer> parseToLocation(String location) {
        if (location.startsWith("#")) {
            return this.server.getServer(location.substring(1));
        } else {
            Optional<Player> player = this.server.getPlayer(location);

            if (player.isPresent()) {
                Optional<ServerConnection> server = player.get().getCurrentServer();
                if (server.isPresent()) {
                    return Optional.of(server.get().getServer());
                }
            }
        }

        return Optional.empty();
    }

    @Override
    protected String[] getAliases() {
        return new String[]{
                "vtp"
        };
    }

    private List<String> getSuggestions(String argument, boolean showAll) {
        List<String> suggestions = new ArrayList<>();

        if (showAll) {
            suggestions.add("@a");
        }
        suggestions.addAll(SuggestionUtil.getServers(this.server, "#"));
        suggestions.addAll(SuggestionUtil.getPlayers(this.server));

        return SuggestionUtil.filterSuggestions(argument, suggestions);
    }
}
