package me.azarex.broadcastit.utility;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.ArrayList;
import java.util.List;

public class ComponentUtility {

    public static List<TextComponent> convert(List<String> list, HoverEvent hover, ClickEvent click) {
        List<TextComponent> components = new ArrayList<>(list.size());

        list.stream()
                .map(TextComponent::new)
                .forEach(component -> {
                    component.setHoverEvent(hover);
                    component.setClickEvent(click);
                    components.add(component);
                });

        return components;
    }
}
