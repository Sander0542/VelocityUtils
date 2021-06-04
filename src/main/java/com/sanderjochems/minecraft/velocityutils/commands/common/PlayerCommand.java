package com.sanderjochems.minecraft.velocityutils.commands.common;

import com.sanderjochems.minecraft.velocityutils.utils.ChatUtil;
import com.sanderjochems.minecraft.velocityutils.utils.SuggestionUtil;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;

import java.util.List;
import java.util.Optional;

public abstract class PlayerCommand extends Command {
    public PlayerCommand(ProxyServer server) {
        super(server);
    }

    public abstract void execute(SimpleCommand.Invocation invocation, Player player);

    @Override
    public void execute(SimpleCommand.Invocation invocation) {
        CommandSource source = invocation.source();
        String[] arguments = invocation.arguments();

        if (arguments.length < 1) {
            ChatUtil.sendMessage(source, String.format("&cUsage: /%s <player>", invocation.alias()));
            return;
        }

        String username = arguments[0];

        Optional<Player> player = this.server.getPlayer(username);

        if (!player.isPresent()) {
            ChatUtil.sendMessage(source, String.format("&c%s is not online", username));
            return;
        }

        this.execute(invocation, player.get());
    }

    @Override
    public List<String> suggest(SimpleCommand.Invocation invocation) {
        String[] arguments = invocation.arguments();
        if (arguments.length > 1) {
            return super.suggest(invocation);
        }

        String username = arguments.length > 0 ? arguments[0] : "";

        return SuggestionUtil.filterSuggestions(username, SuggestionUtil.getPlayers(this.server));
    }
}
