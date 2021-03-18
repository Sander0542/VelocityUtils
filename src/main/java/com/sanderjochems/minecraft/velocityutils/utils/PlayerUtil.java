package com.sanderjochems.minecraft.velocityutils.utils;

import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.ServerConnection;
import com.velocitypowered.api.proxy.server.RegisteredServer;

import java.util.Optional;

public class PlayerUtil {
    public static boolean inServer(Player player, ServerConnection serverConnection) {
        return inServer(player, serverConnection.getServer());
    }

    public static boolean inServer(Player player, RegisteredServer server) {
        Optional<ServerConnection> playerServer = player.getCurrentServer();

        if (!playerServer.isPresent()) {
            return false;
        }

        return playerServer.get().getServer() == server;
    }
}
