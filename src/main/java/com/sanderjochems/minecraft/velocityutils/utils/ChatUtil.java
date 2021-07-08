package com.sanderjochems.minecraft.velocityutils.utils;

import com.sanderjochems.minecraft.velocityutils.Constants;
import com.sanderjochems.minecraft.velocityutils.VelocityUtils;
import com.sanderjochems.minecraft.velocityutils.helpers.HelpHelper;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

import java.util.Map;

public class ChatUtil {
    public static final String ChatPrefix = String.format("&2[&a%s&2]&r ", Constants.PluginName);

    public static void sendMessage(CommandSource source, String message) {
        source.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(message));
    }

    public static void sendList(CommandSource source, String title, Map<String, String> items) {
        sendMessage(source, String.format("&a ====== &f%s&a ======", title));
        for (Map.Entry<String, String> entry : items.entrySet()) {
            ChatUtil.sendMessage(source, formatItem(entry));
        }
    }

    public static void sendHelp(SimpleCommand.Invocation invocation) {
        Map<String, String> commandHelp = HelpHelper.commands(invocation);

        if (commandHelp.isEmpty()) return;

        ChatUtil.sendList(invocation.source(), String.format("%s Help", Constants.PluginName), commandHelp);
    }

    private static String formatItem(Map.Entry<String, String> entry) {
        return formatItem(entry.getKey(), entry.getValue());
    }

    private static String formatItem(String title, String value) {
        return String.format("&a - %s:&f %s", title, value);
    }

    public static void sendVersionCheck(CommandSource source) {
        String currentVersion = VelocityUtils.getInstance().getCurrentVersion();
        String newVersion = VelocityUtils.getInstance().getNewVersion();

        ChatUtil.sendMessage(source, String.format("%s&aThere is a newer plugin version available: &f%s&a, you're on: &f%s", ChatPrefix, newVersion, currentVersion));
    }
}
