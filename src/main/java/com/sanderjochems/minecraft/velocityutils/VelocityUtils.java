package com.sanderjochems.minecraft.velocityutils;

import com.google.inject.Inject;
import com.sanderjochems.minecraft.velocityutils.commands.*;
import com.sanderjochems.minecraft.velocityutils.commands.common.Command;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import org.bstats.velocity.Metrics;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

@Plugin(
        id = "velocity-utils",
        name = "VelocityUtils",
        version = "@version@",
        description = "Useful utilities for a Velocity Minecraft server",
        url = "https://sanderjochems.com/",
        authors = {"Sander Jochems"}
)
public class VelocityUtils {
    private static VelocityUtils Instance;

    private final ProxyServer server;
    private final Logger logger;
    private final Metrics.Factory metricsFactory;
    private final List<Command> commands = new ArrayList<>();

    private static final int BStatsPluginId = 11571;

    public static VelocityUtils getInstance() {
        return Instance;
    }

    @Inject
    public VelocityUtils(final ProxyServer server, final CommandManager commandManager, final Logger logger, Metrics.Factory metricsFactory) {
        Instance = this;

        this.server = server;
        this.logger = logger;
        this.metricsFactory = metricsFactory;

        this.registerCommands(commandManager);
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        Metrics metrics = this.metricsFactory.make(this, BStatsPluginId);
    }

    public List<Command> getCommands() {
        return this.commands;
    }

    private void registerCommands(final CommandManager commandManager) {
        this.commands.add(new AlertCommand(VelocityUtils.this.server));
        this.commands.add(new FindCommand(VelocityUtils.this.server));
        this.commands.add(new IpCommand(VelocityUtils.this.server));
        this.commands.add(new JoinCommand(VelocityUtils.this.server));
        this.commands.add(new ProxyInfoCommand(VelocityUtils.this.server));
        this.commands.add(new SendCommand(VelocityUtils.this.server));
        this.commands.add(new ServerInfoCommand(VelocityUtils.this.server));
        this.commands.add(new MainCommand(VelocityUtils.this.server));

        for (Command command : this.commands) {
            command.register(commandManager);
        }
    }
}
