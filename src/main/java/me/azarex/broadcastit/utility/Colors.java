package me.azarex.broadcastit.utility;

import org.bukkit.ChatColor;

import java.util.List;
import java.util.function.Function;

public class Colors {

    private static final Function<String, String> colorFunction = input -> ChatColor.translateAlternateColorCodes('&', input);

    public static String color(String message) {
        return colorFunction.apply(message);
    }

    public static List<String> color(List<String> color) {
        color.replaceAll(Colors::color);
        return color;
    }

}
