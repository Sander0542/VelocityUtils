package com.sanderjochems.minecraft.velocityutils.commands;

import com.sanderjochems.minecraft.velocityutils.commands.common.ServerCommand;
import com.sanderjochems.minecraft.velocityutils.utils.ChatUtil;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import com.velocitypowered.api.proxy.server.ServerInfo;

import java.util.LinkedHashMap;
import java.util.Map;

public class ServerInfoCommand extends ServerCommand {

    public ServerInfoCommand(ProxyServer server) {
        super(server);
    }

    @Override
    public void execute(Invocation invocation, RegisteredServer server) {
        final String title = String.format("ServerInfo: &c%s", server.getServerInfo().getName());

        ChatUtil.sendList(invocation.source(), title, this.getItems(server));
    }

    private Map<String, String> getItems(RegisteredServer server) {
        final ServerInfo serverInfo = server.getServerInfo();

        return new LinkedHashMap<String, String>() {{
            this.put("Address", serverInfo.getAddress().toString());
            this.put("Players", String.valueOf(server.getPlayersConnected().size()));
        }};
    }

    @Override
    protected String getPermission() {
        return "velocityutils.serverinfo";
    }

    @Override
    public String getCommand() {
        return "serverinfo";
    }

    @Override
    public String getDescription() {
        return "Show information about a given server";
    }
}
