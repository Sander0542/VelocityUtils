package com.sanderjochems.minecraft.velocityutils.commands;

import com.sanderjochems.minecraft.velocityutils.commands.common.BaseCommand;
import com.sanderjochems.minecraft.velocityutils.utils.ChatUtil;
import com.velocitypowered.api.proxy.ProxyServer;

import java.util.LinkedHashMap;
import java.util.Map;

public class ProxyInfoCommand extends BaseCommand {

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
    public String getCommand() {
        return "proxyinfo";
    }

    @Override
    public String getDescription() {
        return "Show information about the proxy server";
    }
}
