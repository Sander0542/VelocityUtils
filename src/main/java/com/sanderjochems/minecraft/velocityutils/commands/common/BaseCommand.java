package com.sanderjochems.minecraft.velocityutils.commands.common;

import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.command.CommandMeta;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.ProxyServer;

import java.util.Collections;
import java.util.List;

public abstract class BaseCommand implements SimpleCommand {

    protected final ProxyServer server;

    public BaseCommand(ProxyServer server) {
        this.server = server;
    }

    protected CommandMeta getMeta(CommandManager commandManager) {
        CommandMeta.Builder builder = commandManager.metaBuilder(this.getCommand());
        if (this.getAliases().length > 0) {
            builder.aliases(this.getAliases());
        }

        return builder.build();
    }

    protected abstract String getPermission();

    public abstract String getCommand();

    protected String[] getAliases() {
        return new String[0];
    }

    public abstract String getDescription();

    @Override
    public boolean hasPermission(Invocation invocation) {
        return this.getPermission() == null || invocation.source().hasPermission(this.getPermission());
    }

    @Override
    public List<String> suggest(Invocation invocation) {
        return Collections.emptyList();
    }

    public void register(CommandManager commandManager) {
        commandManager.register(this.getMeta(commandManager), this);
    }
}
