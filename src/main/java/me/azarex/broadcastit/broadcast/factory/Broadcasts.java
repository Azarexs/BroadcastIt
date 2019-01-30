package me.azarex.broadcastit.broadcast.factory;

import me.azarex.broadcastit.broadcast.messages.BroadcastJsonMessage;
import me.azarex.broadcastit.broadcast.messages.BroadcastMessage;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Broadcasts {

    private Broadcasts() { }

    public static BroadcastMessage singleLine(String message, String permission, Sound sound) {
        return new BroadcastMessage() {
            @Override
            public void send(Player player) {
                player.sendMessage(message);
            }

            @Override
            public boolean multiLined() {
                return false;
            }

            @Override
            public String permission() {
                return permission;
            }

            @Override
            public Sound sound() {
                return sound;
            }
        };
    }

    public static BroadcastMessage multiLine(List<String> messages, String permission, Sound sound) {
        return new BroadcastMessage() {
            @Override
            public void send(Player player) {
                messages.forEach(player::sendMessage);
            }

            @Override
            public boolean multiLined() {
                return true;
            }

            @Override
            public String permission() {
                return permission;
            }

            @Override
            public Sound sound() {
                return sound;
            }
        };
    }

    public static BroadcastJsonMessage singleLineWithJson(String message, String permission, Sound sound, ClickEvent click, HoverEvent hover) {
        TextComponent component = new TextComponent(message);

        component.setClickEvent(click);
        component.setHoverEvent(hover);

        return new BroadcastJsonMessage() {
            @Override
            public void send(Player player) {
                player.spigot().sendMessage(component);
            }

            @Override
            public HoverEvent hover() {
                return hover;
            }

            @Override
            public ClickEvent click() {
                return click;
            }

            @Override
            public boolean multiLined() {
                return false;
            }

            @Override
            public String permission() {
                return permission;
            }

            @Override
            public Sound sound() {
                return sound;
            }
        };
    }

    public static BroadcastJsonMessage multiLineWithJson(List<String> messages, String permission, Sound sound, ClickEvent click, HoverEvent hover) {
        List<TextComponent> components = new ArrayList<>(messages.size());

        messages.forEach(message -> {
            final TextComponent component = new TextComponent(message);
            component.setClickEvent(click);
            component.setHoverEvent(hover);

            components.add(component);
        });

        return new BroadcastJsonMessage() {
            @Override
            public void send(Player player) {
                components.forEach(player.spigot()::sendMessage);
            }

            @Override
            public HoverEvent hover() {
                return hover;
            }

            @Override
            public ClickEvent click() {
                return click;
            }

            @Override
            public boolean multiLined() {
                return true;
            }

            @Override
            public String permission() {
                return permission;
            }

            @Override
            public Sound sound() {
                return sound;
            }
        };
    }

}
