package me.azarex.broadcastit.configuration;

import me.azarex.broadcastit.common.MutableObject;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public interface Configuration {

    Path getPath();
    MutableObject<YamlConfiguration> getConfiguration();

    default Configuration create() {
        Path path = getPath();

        if (Files.notExists(path)) {
            try {
                if (Files.notExists(path.getParent())) {
                    Files.createDirectories(path.getParent());
                }

                Files.createFile(path);
                Files.copy(getClass().getResourceAsStream("/" + path.getFileName()), path, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        getConfiguration().setValue(YamlConfiguration.loadConfiguration(path.toFile()));
        return this;
    }

    default void save() {
        try {
            getConfiguration().getValue().save(getPath().toFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    default void reload() {
        save();
        getConfiguration().setValue(YamlConfiguration.loadConfiguration(getPath().toFile()));
    }

    default void set(String key, Object value) {
        getConfiguration().getValue().set(key, value);
    }

    default Object get(String key) {
        return getConfiguration().getValue().get(key);
    }

    static Configuration of(Path path) {
        MutableObject<YamlConfiguration> configuration = new MutableObject<>(null);
        return new Configuration() {
            @Override
            public Path getPath() {
                return path;
            }

            @Override
            public MutableObject<YamlConfiguration> getConfiguration() {
                return configuration;
            }
        }.create();
    }
}
