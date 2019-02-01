package me.azarex.broadcastit.broadcast.messages;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

public interface BroadcastMessage {

    void send(Player player);
    String permission(); // Required permission for players to see said permission.
    Sound sound();       // Sound played on broadcast
}
