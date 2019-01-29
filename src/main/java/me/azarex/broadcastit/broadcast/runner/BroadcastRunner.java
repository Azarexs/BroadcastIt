package me.azarex.broadcastit.broadcast.runner;

import me.azarex.broadcastit.broadcast.messages.BroadcastMessage;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

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
        final Sound sound = message.sound();

        players.forEach(player -> {
            player.playSound(player.getLocation(), sound, 1.0f, 1.0f);
            message.send(player);
        });
    }
}
