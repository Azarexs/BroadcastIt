package me.azarex.broadcastit.command;

import me.azarex.broadcastit.command.executor.Executable;
import me.azarex.broadcastit.command.executor.Executor;
import me.azarex.broadcastit.configuration.Configuration;

import java.util.HashMap;
import java.util.Map;

public class CommandManager {

    private Map<String, Executable> commands;
    private Executor primaryExecutor;

    public CommandManager(Configuration languageConfiguration) {
        commands = new HashMap<>();
        primaryExecutor = new Executor(command -> commands.get(command), languageConfiguration);
    }

    public CommandManager register(Executable executable) {
        commands.put(executable.name(), executable);
        executable.alias().forEach(name -> commands.put(name, executable));
        return this;
    }


    public Executor executor() {
        return primaryExecutor;
    }

}
