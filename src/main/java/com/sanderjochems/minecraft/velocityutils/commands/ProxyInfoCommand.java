package com.sanderjochems.minecraft.velocityutils.commands;

import com.sanderjochems.minecraft.velocityutils.utils.ChatUtil;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.command.CommandMeta;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import com.velocitypowered.api.proxy.server.ServerInfo;

import java.util.*;

public class ProxyInfoCommand extends VelocityUtilsCommand {

    public ProxyInfoCommand(ProxyServer server) {
        super(server);
    }

    @Override
    public void execute(Invocation invocation) {
        ChatUtil.sendList(invocation.source(), "ProxyInfo", this.getItems(this.server));
    }

    private Map<String, String> getItems(ProxyServer server) {
        return new LinkedHashMap<String, String>() {{
            this.put("Address", server.getBoundAddress().toString());
            this.put("Version", String.format("%s (%s)", server.getVersion().getName(), server.getVersion().getVersion()));
            this.put("Players", String.valueOf(server.getPlayerCount()));
            this.put("Servers", String.valueOf(server.getAllServers().size()));
            this.put("Plugins", String.valueOf(server.getPluginManager().getPlugins().size()));
            this.put("Online Mode", server.getConfiguration().isOnlineMode() ? "Yes" : "No");
            this.put("Announce Forge", server.getConfiguration().isAnnounceForge() ? "Yes" : "No");
        }};
    }

    @Override
    protected String getPermission() {
        return "velocityutils.proxyinfo";
    }

    @Override
    protected CommandMeta getMeta(CommandManager commandManager) {
        return commandManager.metaBuilder("proxyinfo").build();
    }
}
