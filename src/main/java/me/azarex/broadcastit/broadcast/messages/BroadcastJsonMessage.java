package me.azarex.broadcastit.broadcast.messages;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;

public interface BroadcastJsonMessage extends BroadcastMessage {

    HoverEvent hover(); // Represents the hover event for the BroadcastMessage
    ClickEvent click(); // Represents the click event for the BroadcastMessage

}
