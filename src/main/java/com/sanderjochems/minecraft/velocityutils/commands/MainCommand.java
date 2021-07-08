package com.sanderjochems.minecraft.velocityutils.commands;

import com.sanderjochems.minecraft.velocityutils.VelocityUtils;
import com.sanderjochems.minecraft.velocityutils.commands.common.BaseCommand;
import com.sanderjochems.minecraft.velocityutils.utils.ChatUtil;
import com.sanderjochems.minecraft.velocityutils.utils.SuggestionUtil;
import com.velocitypowered.api.plugin.PluginDescription;
import com.velocitypowered.api.proxy.ProxyServer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainCommand extends BaseCommand {

    private static final String PREFIX_PERMISSION = "velocityutils.";

    private static final String SUB_HELP = "help";

    public MainCommand(ProxyServer server) {
        super(server);
    }

    @Override
    public void execute(Invocation invocation) {
        String[] arguments = invocation.arguments();

        if (arguments.length == 0) {
            ChatUtil.sendHelp(invocation);
            return;
        }

        switch (arguments[0]) {
            case SUB_HELP:
                ChatUtil.sendHelp(invocation);
                return;
        }

        ChatUtil.sendMessage(invocation.source(), "Invalid subcommand");
    }

    @Override
    public List<String> suggest(Invocation invocation) {
        String[] arguments = invocation.arguments();

        if (arguments.length <= 1) {
            return SuggestionUtil.filterSuggestions(arguments.length > 0 ? arguments[0] : "", this.getSubCommands(invocation));
        }

        return super.suggest(invocation);
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

    private boolean hasPermission(Invocation invocation, String subCommand) {
        if (subCommand.equals(SUB_HELP)) {
            return true;
        }

        return invocation.source().hasPermission(PREFIX_PERMISSION + subCommand);
    }

    private List<String> getSubCommands(Invocation invocation) {
        List<String> subCommands = new ArrayList<>() {{
            this.add(SUB_HELP);
        }};

        subCommands.removeIf(subCommand -> !this.hasPermission(invocation, subCommand));

        return subCommands;
    }
}
