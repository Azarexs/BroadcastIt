package me.azarex.broadcastit.command.commands;

import me.azarex.broadcastit.command.executor.Executable;
import me.azarex.broadcastit.configuration.Configuration;
import me.azarex.broadcastit.utility.Colors;
import org.bukkit.command.CommandSender;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HelpCommand implements Executable {

    private Configuration configuration;

    public HelpCommand(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        List<String> helpMenu = Colors.color((List<String>) configuration.get("Language.Help-menu"));
        helpMenu.forEach(sender::sendMessage);
    }

    @Override
    public String name() {
        return "help";
    }

    @Override
    public Set<String> alias() {
        return Collections.unmodifiableSet(new HashSet<>(Collections.singletonList("h")));
    }
}
