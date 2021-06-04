package com.sanderjochems.minecraft.velocityutils.commands;

import com.sanderjochems.minecraft.velocityutils.VelocityUtils;
import com.sanderjochems.minecraft.velocityutils.commands.common.Command;
import com.sanderjochems.minecraft.velocityutils.utils.ChatUtil;
import com.velocitypowered.api.proxy.ProxyServer;

import java.util.LinkedHashMap;
import java.util.Map;

public class MainCommand extends Command {

    public MainCommand(ProxyServer server) {
        super(server);
    }

    @Override
    public void execute(Invocation invocation) {
        Map<String, String> commandHelp = new LinkedHashMap<>();

        for (Command command : VelocityUtils.getInstance().getCommands()) {
            if (command.hasPermission(invocation)) {
                commandHelp.put(String.format("/%s", command.getCommand()), command.getDescription());
            }
        }

        if (commandHelp.isEmpty()) return;

        ChatUtil.sendList(invocation.source(), "VelocityUtils Help", commandHelp);
    }

    @Override
    protected String getPermission() {
        return null;
    }

    @Override
    public String getCommand() {
        return "velocityutils";
    }

    @Override
    protected String[] getAliases() {
        return new String[]{
                "vutils"
        };
    }

    @Override
    public String getDescription() {
        return "Show the help page of the VelocityUtils plugin";
    }
}
