package me.azarex.broadcastit.broadcast.messages.implementation.json;

import me.azarex.broadcastit.broadcast.messages.BroadcastJsonMessage;
import me.azarex.broadcastit.broadcast.messages.implementation.SingleLineMessage;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Sound;


public class SingleLineJsonMessage implements BroadcastJsonMessage {

    private TextComponent message;
    private String permission;
    private Sound sound;

    private HoverEvent hover;
    private ClickEvent click;

    public SingleLineJsonMessage(SingleLineMessage from, HoverEvent hover, ClickEvent click) {
        message = new TextComponent(from.message());
        permission = from.permission();
        sound = from.sound();

        this.hover = hover;
        this.click = click;

        message.setHoverEvent(hover);
        message.setClickEvent(click);
    }

    public TextComponent message() {
        return message;
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

    @Override
    public HoverEvent hover() {
        return hover;
    }

    @Override
    public ClickEvent click() {
        return click;
    }
}
