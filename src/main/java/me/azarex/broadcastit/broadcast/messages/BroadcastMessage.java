package me.azarex.broadcastit.broadcast.messages;

import org.bukkit.Sound;

public interface BroadcastMessage {

    boolean multiLined();
    String permission(); // Required permission for players to see said permission.
    Sound sound();       // Sound played on broadcast
}
