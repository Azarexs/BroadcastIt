package me.azarex.broadcastit.broadcast.messages.implementation;

import me.azarex.broadcastit.broadcast.messages.BroadcastMessage;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.Sound;

public class SingleLineMessage implements BroadcastMessage {

    private String message;
    private String permission;
    private Sound sound;

    public SingleLineMessage(String message, String permission, Sound sound) {
        this.message = message;
        this.permission = permission;
        this.sound = sound;
    }

    public String message() {
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
}
