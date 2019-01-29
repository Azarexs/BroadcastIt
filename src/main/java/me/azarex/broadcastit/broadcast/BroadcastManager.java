package me.azarex.broadcastit.broadcast;

import me.azarex.broadcastit.broadcast.factory.Broadcasts;
import me.azarex.broadcastit.broadcast.messages.BroadcastMessage;
import me.azarex.broadcastit.broadcast.runner.BroadcastRunner;
import me.azarex.broadcastit.configuration.Configuration;
import me.azarex.broadcastit.utility.Colors;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BroadcastManager {

    private Queue<BroadcastMessage> messages;
    private BroadcastRunner runner;

    private static final String MESSAGE_PATH = ".message";

    public BroadcastManager(Configuration configuration) {
        messages = new LinkedList<>();
        runner = new BroadcastRunner(this::next);

        load(configuration);
    }

    private void load(Configuration configuration) {
        ConfigurationSection section = (ConfigurationSection) configuration.get("messages");

        for (String key : section.getKeys(false)) {
            final String permission = (String) section.get(key + ".permission");
            final Sound sound = Sound.valueOf((String) section.get(key + ".sound"));

            final ConfigurationSection subSection = (ConfigurationSection) section.get(key);

            if (section.get(key + MESSAGE_PATH) instanceof List) {
                final List<String> message = Colors.color((List<String>) section.get(key + MESSAGE_PATH));

                if (subSection.contains("click") || subSection.contains("hover")) {
                    // ADD JSON SUPPORT
                }

                messages.add(Broadcasts.multiLine(message, permission, sound));
                continue;
            }

            final String message = Colors.color((String) section.get(key + MESSAGE_PATH));

            if (subSection.contains("click") || subSection.contains("hover")) {
                // ADD JSON SUPPORT
            }

            messages.add(Broadcasts.singleLine(message, permission, sound));
        }
    }

    private BroadcastMessage next() {
        if (messages.isEmpty()) {
            return null;
        }

        messages.add(messages.peek());
        return messages.remove();
    }

    public BroadcastRunner getRunner() {
        return runner;
    }

}
