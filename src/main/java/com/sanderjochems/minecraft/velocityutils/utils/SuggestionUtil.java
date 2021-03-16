package com.sanderjochems.minecraft.velocityutils.utils;

import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.RegisteredServer;

import java.util.ArrayList;
import java.util.List;

public class SuggestionUtil {
    public static List<String> filterSuggestions(String argument, List<String> suggestions) {
        List<String> filteredSuggestions = new ArrayList<>();

        for (String suggestion : suggestions) {
            if (argument.isEmpty() || suggestion.toUpperCase().startsWith(argument.toUpperCase())) {
                filteredSuggestions.add(suggestion);
            }
        }

        return filteredSuggestions;
    }

    public static List<String> getPlayers(ProxyServer proxyServer) {
        List<String> players = new ArrayList<>();

        for (Player player : proxyServer.getAllPlayers()) {
            players.add(player.getUsername());
        }

        return players;
    }

    public static List<String> getServers(ProxyServer proxyServer) {
        List<String> servers = new ArrayList<>();

        for (RegisteredServer server : proxyServer.getAllServers()) {
            servers.add("#" + server.getServerInfo().getName());
        }

        return servers;
    }
}
