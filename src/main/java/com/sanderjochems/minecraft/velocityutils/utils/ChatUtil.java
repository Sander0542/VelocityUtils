package com.sanderjochems.minecraft.velocityutils.utils;

import com.velocitypowered.api.command.CommandSource;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

import java.util.Map;

public class ChatUtil {
    public static void sendMessage(CommandSource source, String message) {
        source.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(message));
    }

    public static void sendList(CommandSource source, String title, Map<String, String> items) {
        sendMessage(source, String.format("&a ====== &f%s&a ======", title));
        for (Map.Entry<String, String> entry : items.entrySet()) {
            ChatUtil.sendMessage(source, formatItem(entry));
        }
    }

    private static String formatItem(Map.Entry<String, String> entry) {
        return formatItem(entry.getKey(), entry.getValue());
    }

    private static String formatItem(String title, String value) {
        return String.format("&a - %s:&f %s", title, value);
    }
}
