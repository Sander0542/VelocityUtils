package com.sanderjochems.minecraft.velocityutils.helpers;

import com.sanderjochems.minecraft.velocityutils.VelocityUtils;
import com.sanderjochems.minecraft.velocityutils.commands.common.BaseCommand;
import com.velocitypowered.api.command.SimpleCommand;

import java.util.LinkedHashMap;
import java.util.Map;

public class HelpHelper {
    public static Map<String, String> commands(SimpleCommand.Invocation invocation) {
        Map<String, String> commandHelp = new LinkedHashMap<>();

        for (BaseCommand command : VelocityUtils.getInstance().getCommands()) {
            if (command.hasPermission(invocation)) {
                commandHelp.put(String.format("/%s", command.getCommand()), command.getDescription());
            }
        }

        return commandHelp;
    }
}
