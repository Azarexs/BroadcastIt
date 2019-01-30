package me.azarex.broadcastit.broadcast;

import me.azarex.broadcastit.broadcast.factory.Broadcasts;
import me.azarex.broadcastit.broadcast.messages.BroadcastMessage;
import me.azarex.broadcastit.broadcast.runner.BroadcastRunner;
import me.azarex.broadcastit.configuration.Configuration;
import me.azarex.broadcastit.utility.Colors;
import net.md_5.bungee.api.chat.*;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BroadcastManager {

    private Queue<BroadcastMessage> messages;
    private BroadcastRunner runner;

    private static final String MESSAGE_PATH = ".message",
                                HOVER_PATH = "hover",
                                CLICK_PATH = "click";

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

                if (subSection.contains(CLICK_PATH) || subSection.contains(HOVER_PATH)) {
                    messages.add(Broadcasts.multiLineWithJson(message, permission, sound, findClick(subSection), findHover(subSection)));
                    continue;
                }

                messages.add(Broadcasts.multiLine(message, permission, sound));
                continue;
            }

            final String message = Colors.color((String) section.get(key + MESSAGE_PATH));

            if (subSection.contains(CLICK_PATH) || subSection.contains(HOVER_PATH)) {
                messages.add(Broadcasts.singleLineWithJson(message, permission, sound, findClick(subSection), findHover(subSection)));
                continue;
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

    private ClickEvent findClick(ConfigurationSection section) {
        if (!section.contains(CLICK_PATH)) {
            return null;
        }

        return new ClickEvent(ClickEvent.Action.RUN_COMMAND, section.get(CLICK_PATH).toString());
    }

    private HoverEvent findHover(ConfigurationSection section) {
        if (!section.contains(HOVER_PATH)) {
            return null;
        }

        return new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(section.get(HOVER_PATH).toString()).create());
    }

    public BroadcastRunner getRunner() {
        return runner;
    }

}
