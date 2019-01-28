package me.azarex.broadcastit.configuration;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ConfigurationService<T> {

    private Map<T, Configuration> configurations;

    public ConfigurationService() {
        configurations = new HashMap<>();
    }

    public void saveAndReloadAll() {
        configurations.values().forEach(configuration -> {
            configuration.save();
            configuration.reload();
        });
    }

    public Configuration register(T key, Path configurationPath) {
        return configurations.put(key, Configuration.of(configurationPath));
    }

    public Optional<Configuration> get(T key) {
        return configurations.containsKey(key) ? Optional.of(configurations.get(key)) : Optional.empty();
    }

}
