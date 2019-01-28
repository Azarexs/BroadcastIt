package me.azarex.broadcastit.broadcast.messages.implementation.json;

import me.azarex.broadcastit.broadcast.messages.BroadcastJsonMessage;
import me.azarex.broadcastit.broadcast.messages.implementation.MultiLineMessage;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Sound;

import java.util.ArrayList;
import java.util.List;

public class MultiLineJsonMessage implements BroadcastJsonMessage {

    private List<TextComponent> components;
    private String permission;
    private Sound sound;

    private HoverEvent hover;
    private ClickEvent click;

    public MultiLineJsonMessage(MultiLineMessage other, HoverEvent hover, ClickEvent click) {
        components = new ArrayList<>();
        other.message().forEach(message -> {
            TextComponent component = new TextComponent(message);
            component.setHoverEvent(hover);
            component.setClickEvent(click);

            components.add(component);
        });

        permission = other.permission();
        sound = other.sound();

        this.hover = hover;
        this.click = click;
    }

    public List<TextComponent> message() {
        return components;
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

    @Override
    public HoverEvent hover() {
        return hover;
    }

    @Override
    public ClickEvent click() {
        return click;
    }
}
