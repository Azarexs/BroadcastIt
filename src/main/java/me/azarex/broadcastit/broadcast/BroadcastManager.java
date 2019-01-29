package me.azarex.broadcastit.broadcast;

import me.azarex.broadcastit.broadcast.messages.BroadcastMessage;
import me.azarex.broadcastit.broadcast.messages.implementation.MultiLineMessage;
import me.azarex.broadcastit.broadcast.messages.implementation.SingleLineMessage;
import me.azarex.broadcastit.broadcast.runner.BroadcastRunner;
import me.azarex.broadcastit.configuration.Configuration;
import me.azarex.broadcastit.utility.Colors;
import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BroadcastManager {

    private Queue<BroadcastMessage> messages;
    private BroadcastRunner runner;

    public BroadcastManager(Configuration configuration) {
        messages = new LinkedList<>();
        runner = new BroadcastRunner(this::next);

        load(configuration);
    }

    private void load(Configuration configuration) {
        ConfigurationSection section = (ConfigurationSection) configuration.get("messages");

        for (String key : section.getKeys(false)) {
            final ConfigurationSection subSection = (ConfigurationSection) configuration.get(key);
            final String permission = (String) section.get(key + ".permission");
            final Sound sound = Sound.valueOf((String) section.get(key + ".sound"));

            if (section.get(key + ".message") instanceof List) {
                final List<String> message = Colors.color((List<String>) section.get(key + ".message"));
                messages.add(new MultiLineMessage(message, permission, sound));

                continue;
            }

            final String message = Colors.color((String) section.get(key + ".message"));
            messages.add(new SingleLineMessage(message, permission, sound));
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
