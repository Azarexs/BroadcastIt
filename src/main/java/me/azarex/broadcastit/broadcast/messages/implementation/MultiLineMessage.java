package me.azarex.broadcastit.broadcast.messages.implementation;

import me.azarex.broadcastit.broadcast.messages.BroadcastMessage;
import org.bukkit.Sound;

import java.util.List;

public class MultiLineMessage implements BroadcastMessage {

    private List<String> message;
    private String permission;
    private Sound sound;

    public MultiLineMessage(List<String> message, String permission, Sound sound) {
        this.message = message;
        this.permission = permission;
        this.sound = sound;
    }

    public List<String> message() {
        return message;
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
}
