package com.sanderjochems.minecraft.velocityutils.commands;

import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.command.CommandMeta;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.ProxyServer;

import java.util.Collections;
import java.util.List;

public abstract class VelocityUtilsCommand implements SimpleCommand {

    protected final ProxyServer server;

    public VelocityUtilsCommand(ProxyServer server) {
        this.server = server;
    }

    protected abstract CommandMeta getMeta(CommandManager commandManager);

    protected abstract String getPermission();

    @Override
    public boolean hasPermission(Invocation invocation) {
        return invocation.source().hasPermission(this.getPermission());
    }

    @Override
    public List<String> suggest(Invocation invocation) {
        return Collections.emptyList();
    }

    public void register(CommandManager commandManager) {
        commandManager.register(this.getMeta(commandManager), this);
    }
}
