package me.azarex.broadcastit.broadcast;

import me.azarex.broadcastit.broadcast.messages.BroadcastBuilder;
import me.azarex.broadcastit.broadcast.messages.BroadcastMessage;
import me.azarex.broadcastit.broadcast.runner.BroadcastRunner;
import me.azarex.broadcastit.configuration.Configuration;
import me.azarex.broadcastit.utility.Colors;
import me.azarex.broadcastit.utility.ComponentUtility;
import net.md_5.bungee.api.chat.*;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BroadcastManager {

    private Queue<BroadcastMessage> messages;
    private BroadcastRunner runner;

    private static final String MESSAGE_PATH = ".Message";
    private static final String HOVER_PATH = "Hover";
    private static final String CLICK_PATH = "Click";

    public BroadcastManager(Configuration configuration) {
        messages = new LinkedList<>();
        runner = new BroadcastRunner(this::next);

        load(configuration);
    }

    private void load(Configuration configuration) {
        ConfigurationSection section = (ConfigurationSection) configuration.get("messages");

        for (String key : section.getKeys(false)) {
            final String permission = (String) section.get(key + ".Permission");
            final Sound sound = Sound.valueOf((String) section.get(key + ".Sound"));
            final ConfigurationSection subSection = (ConfigurationSection) section.get(key);

            final BroadcastBuilder builder = BroadcastBuilder.empty()
                    .sound(sound)
                    .permission(permission);

            if (section.get(key + MESSAGE_PATH) instanceof List) {
                final List<String> message = Colors.color((List<String>) section.get(key + MESSAGE_PATH));

                if (subSection.contains(CLICK_PATH) || subSection.contains(HOVER_PATH)) {
                    List<TextComponent> components = ComponentUtility.convert(message, findHover(subSection), findClick(subSection));
                    BroadcastMessage broadcastMessage = builder
                            .send(player -> components.forEach(component -> player.spigot().sendMessage(component)))
                            .toBroadcast();

                    messages.add(broadcastMessage);
                    continue;
                }

                BroadcastMessage broadcastMessage = builder
                        .send(player -> message.forEach(player::sendMessage))
                        .toBroadcast();

                messages.add(broadcastMessage);
                continue;
            }

            final String message = Colors.color(configuration.get("Language.Prefix").toString() + section.get(key + MESSAGE_PATH));

            if (subSection.contains(CLICK_PATH) || subSection.contains(HOVER_PATH)) {
                final TextComponent component = new TextComponent(message);
                component.setHoverEvent(findHover(subSection));
                component.setClickEvent(findClick(subSection));

                BroadcastMessage broadcastMessage = builder
                        .send(player -> player.spigot().sendMessage(component))
                        .toBroadcast();

                messages.add(broadcastMessage);
                continue;
            }

            BroadcastMessage broadcastMessage = builder
                    .send(player -> player.sendMessage(message))
                    .toBroadcast();

            messages.add(broadcastMessage);
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
