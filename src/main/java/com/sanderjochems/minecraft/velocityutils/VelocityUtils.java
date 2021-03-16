package com.sanderjochems.minecraft.velocityutils;

import com.google.inject.Inject;
import com.sanderjochems.minecraft.velocityutils.commands.FindCommand;
import com.sanderjochems.minecraft.velocityutils.commands.SendCommand;
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

    @Inject
    public VelocityUtils(final ProxyServer server, final CommandManager commandManager, final Logger logger) {
        this.server = server;

        this.registerCommands(commandManager);
    }

    private final ProxyServer server;

    private void registerCommands(final CommandManager commandManager) {
        new FindCommand(this.server).register(commandManager);
        new SendCommand(this.server).register(commandManager);
    }
}
