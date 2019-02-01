package me.azarex.broadcastit.broadcast.messages;

import me.azarex.broadcastit.common.Mutable;
import me.azarex.broadcastit.common.MutableObject;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.function.Consumer;

public interface BroadcastBuilder {

    Mutable<Consumer<Player>> send();
    Mutable<String> permission();
    Mutable<Sound> sound();

    default BroadcastBuilder send(Consumer<Player> sendAction) {
        send().setValue(sendAction);
        return this;
    }

    default BroadcastBuilder permission(String permission) {
        permission().setValue(permission);
        return this;
    }

    default BroadcastBuilder sound(Sound sound) {
        sound().setValue(sound);
        return this;
    }

    default BroadcastMessage toBroadcast() {
        return new BroadcastMessage() {
            @Override
            public void send(Player player) {
                BroadcastBuilder.this.send().getValue().accept(player);
            }

            @Override
            public String permission() {
                return BroadcastBuilder.this.permission().getValue();
            }

            @Override
            public Sound sound() {
                return BroadcastBuilder.this.sound().getValue();
            }
        };
    }

    static BroadcastBuilder empty() {
        final Mutable<Consumer<Player>> send = new MutableObject<>();
        final Mutable<String> permission = new MutableObject<>();
        final Mutable<Sound> sound = new MutableObject<>();

        return new BroadcastBuilder() {
            @Override
            public Mutable<Consumer<Player>> send() {
                return send;
            }

            @Override
            public Mutable<String> permission() {
                return permission;
            }

            @Override
            public Mutable<Sound> sound() {
                return sound;
            }
        };
    }

}
