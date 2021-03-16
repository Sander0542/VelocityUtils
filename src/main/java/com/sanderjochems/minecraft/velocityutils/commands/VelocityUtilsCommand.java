package com.sanderjochems.minecraft.velocityutils.commands;

import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.command.CommandMeta;
import com.velocitypowered.api.command.SimpleCommand;

public interface VelocityUtilsCommand extends SimpleCommand {
    CommandMeta getMeta(CommandManager commandManager);

    default void register(CommandManager commandManager) {
        commandManager.register(this.getMeta(commandManager), this);
    }
}
