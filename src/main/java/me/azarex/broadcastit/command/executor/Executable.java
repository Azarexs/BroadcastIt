package me.azarex.broadcastit.command.executor;

import org.bukkit.command.CommandSender;

import java.util.Set;

public interface Executable {

    void execute(CommandSender sender, String[] args);

    String name();
    Set<String> alias();
}
