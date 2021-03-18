package com.sanderjochems.minecraft.velocityutils;

import com.google.inject.Inject;
import com.sanderjochems.minecraft.velocityutils.commands.*;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import org.slf4j.Logger;

@Plugin(
        id = "velocity-utils",
        name = "VelocityUtils",
        version = "@version@",
        description = "Useful utilities for a Velocity Minecraft server",
        url = "https://sanderjochems.com/",
        authors = {"Sander Jochems"}
)
public class VelocityUtils {

    private final ProxyServer server;

    private final Logger logger;

    @Inject
    public VelocityUtils(final ProxyServer server, final CommandManager commandManager, final Logger logger) {
        this.server = server;
        this.logger = logger;

        this.registerCommands(commandManager);
    }

    private void registerCommands(final CommandManager commandManager) {
        new AlertCommand(this.server).register(commandManager);
        new FindCommand(this.server).register(commandManager);
        new IpCommand(this.server).register(commandManager);
        new JoinCommand(this.server).register(commandManager);
        new ProxyInfoCommand(this.server).register(commandManager);
        new SendCommand(this.server).register(commandManager);
        new ServerInfoCommand(this.server).register(commandManager);
    }
}
