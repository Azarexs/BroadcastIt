package me.azarex.broadcastit;

import me.azarex.broadcastit.broadcast.BroadcastManager;
import me.azarex.broadcastit.command.CommandManager;
import me.azarex.broadcastit.command.commands.BroadcastCommand;
import me.azarex.broadcastit.configuration.Configuration;
import me.azarex.broadcastit.configuration.ConfigurationService;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.nio.file.Paths;
import java.util.Optional;

public class BroadcastIt extends JavaPlugin {

    @Override
    public void onEnable() {
        ConfigurationService<String> configurations = new ConfigurationService<>();
        configurations.register("config", Paths.get(getDataFolder().toPath() + File.separator + "config.yml"));

        Optional<Configuration> optional = configurations.get("config");

        if (!optional.isPresent()) {
            getLogger().severe("Fatal error: Could not setup / detect config.yml, plugin disabling!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        Configuration configuration = optional.get();
        BroadcastManager broadcastManager = new BroadcastManager(optional.get());

        final int time = (int) configuration.get("Cooldown-interval");
        getServer().getScheduler().runTaskTimer(this, broadcastManager.getRunner(), 0L, time * 20L);

        CommandManager commandManager = new CommandManager(optional.get());
        commandManager.register(new BroadcastCommand(optional.get()));

        getCommand("broadcastit").setExecutor(commandManager.executor());
    }
}
