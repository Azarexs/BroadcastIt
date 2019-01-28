package me.azarex.broadcastit.broadcast.runner;

import me.azarex.broadcastit.broadcast.messages.BroadcastJsonMessage;
import me.azarex.broadcastit.broadcast.messages.BroadcastMessage;
import me.azarex.broadcastit.broadcast.messages.implementation.MultiLineMessage;
import me.azarex.broadcastit.broadcast.messages.implementation.SingleLineMessage;
import me.azarex.broadcastit.broadcast.messages.implementation.json.MultiLineJsonMessage;
import me.azarex.broadcastit.broadcast.messages.implementation.json.SingleLineJsonMessage;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class BroadcastRunner implements Runnable {

    private Supplier<BroadcastMessage> supplier;

    public BroadcastRunner(Supplier<BroadcastMessage> supplier) {
        this.supplier = supplier;
    }

    @Override
    public void run() {
        BroadcastMessage message = supplier.get();

        if (message == null) {
            return;
        }

        Set<Player> players = Bukkit.getOnlinePlayers().stream()
                .filter(player -> {
                    if (message.permission().isEmpty() || message.permission().trim().isEmpty()) {
                        return true;
                    }

                    return player.hasPermission(message.permission());
                }).collect(Collectors.toSet());

        handleMessage(players, message);
    }

    private void handleMessage(Set<Player> players, BroadcastMessage message) {
        if (message instanceof BroadcastJsonMessage) {
            if (message.multiLined()) {
                MultiLineJsonMessage jsonMessage = (MultiLineJsonMessage) message;
                List<TextComponent> broadcast = jsonMessage.message();

                players.forEach(player -> {
                    player.playSound(player.getLocation(), jsonMessage.sound(), 1.0f, 1.0f);
                    broadcast.forEach(player.spigot()::sendMessage);
                });
            }

            SingleLineJsonMessage jsonMessage = (SingleLineJsonMessage) message;
            TextComponent component = jsonMessage.message();

            players.forEach(player -> {
                player.playSound(player.getLocation(), jsonMessage.sound(), 1.0f, 1.0f);
                player.spigot().sendMessage(component);
            });

            return;
        }

        if (message.multiLined()) {
            MultiLineMessage multiLineMessage = (MultiLineMessage) message;
            List<String> broadcast = multiLineMessage.message();

            players.forEach(player -> {
                player.playSound(player.getLocation(), multiLineMessage.sound(), 1.0f, 1.0f);
                broadcast.forEach(player::sendMessage);
            });

            return;
        }

        SingleLineMessage singleLineMessage = (SingleLineMessage) message;

        players.forEach(player -> {
            player.playSound(player.getLocation(), singleLineMessage.sound(), 1.0f, 1.0f);
            player.sendMessage(singleLineMessage.message());
        });
    }
}
