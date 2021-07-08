package com.sanderjochems.minecraft.velocityutils;

import com.google.inject.Inject;
import com.sanderjochems.minecraft.velocityutils.commands.*;
import com.sanderjochems.minecraft.velocityutils.commands.common.BaseCommand;
import com.sanderjochems.minecraft.velocityutils.tasks.VersionCheckerTask;
import com.sanderjochems.minecraft.velocityutils.tasks.common.BaseTask;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.PluginContainer;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.scheduler.Scheduler;
import org.bstats.velocity.Metrics;
import org.slf4j.Logger;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Plugin(
        id = Constants.PluginId,
        name = Constants.PluginName,
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
    private final List<BaseCommand> commands = new ArrayList<>();
    private final Map<BaseTask, Duration> tasks = new HashMap<>();

    private String newestVersion = null;

    public static VelocityUtils getInstance() {
        return Instance;
    }

    public static PluginContainer getPlugin() {
        return getInstance().server.getPluginManager().getPlugin(Constants.PluginId).get();
    }

    public static Logger getLogger() {
        return getInstance().logger;
    }

    @Inject
    public VelocityUtils(final ProxyServer server, final Logger logger, Metrics.Factory metricsFactory) {
        Instance = this;

        this.server = server;
        this.logger = logger;
        this.metricsFactory = metricsFactory;
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        Metrics metrics = this.metricsFactory.make(this, Constants.BStatsPluginId);

        this.registerCommands(this.server.getCommandManager());
        this.registerTasks(this.server.getScheduler());
    }

    public void setNewestVersion(String newestVersion) {
        this.newestVersion = newestVersion;
    }

    public String getCurrentVersion() {
        return getPlugin().getDescription().getVersion().orElse("Unknown");
    }

    public String getNewVersion() {
        return this.newestVersion;
    }

    public boolean isNewerVersionAvailable() {
        return !getPlugin().getDescription().getVersion().orElse("Unknown").equals(this.newestVersion);
    }

    public List<BaseCommand> getCommands() {
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

        for (BaseCommand command : this.commands) {
            command.register(commandManager);
        }
    }

    public Map<BaseTask, Duration> getTasks() {
        return this.tasks;
    }

    private void registerTasks(final Scheduler scheduler) {
        this.tasks.put(new VersionCheckerTask(this.server), Duration.ofMinutes(15));

        for (Map.Entry<BaseTask, Duration> map : this.tasks.entrySet()) {
            scheduler.buildTask(this, map.getKey()).repeat(map.getValue()).schedule();
        }
    }
}
