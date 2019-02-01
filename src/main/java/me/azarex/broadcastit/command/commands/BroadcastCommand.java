package me.azarex.broadcastit.command.commands;

import me.azarex.broadcastit.command.executor.Executable;
import me.azarex.broadcastit.configuration.Configuration;
import me.azarex.broadcastit.utility.Colors;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class BroadcastCommand implements Executable {

    private Configuration configuration;

    public BroadcastCommand(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(Colors.color(configuration.get("Language.Prefix", "Language.Not-enough-arguments")));
            return;
        }

        StringBuilder builder = new StringBuilder();

        for (String argument : args) {
            builder.append(argument).append(' ');
        }

        String broadcast = Colors.color(builder.toString());
        Bukkit.getOnlinePlayers().forEach(player -> player.sendMessage(broadcast));
    }

    @Override
    public String name() {
        return "broadcast";
    }

    @Override
    public Set<String> alias() {
        return Collections.unmodifiableSet(new HashSet<>(Collections.singletonList("bc")));
    }
}
