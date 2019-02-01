package me.azarex.broadcastit.command.executor;

import me.azarex.broadcastit.configuration.Configuration;
import me.azarex.broadcastit.utility.Colors;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.function.Function;

public class Executor implements CommandExecutor {

    private Function<String, Executable> commandFunction;
    private Configuration configuration;

    private static final String DEFAULT_COMMAND = "help";

    public Executor(Function<String, Executable> commandFunction, Configuration configuration) {
        this.commandFunction = commandFunction;
        this.configuration = configuration;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            commandFunction.apply(DEFAULT_COMMAND).execute(sender, new String[0]);
            return true;
        }

        Executable executable = commandFunction.apply(args[0]);

        if (executable == null) {
            sender.sendMessage(Colors.color(configuration.get("Language.Prefix", "Language.Invalid-subcommand")));
            return true;
        }

        executable.execute(sender, Arrays.copyOfRange(args, 1, args.length));
        return true;
    }
}
