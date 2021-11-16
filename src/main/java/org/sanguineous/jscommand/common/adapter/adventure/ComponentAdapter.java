package org.sanguineous.jscommand.common.adapter.adventure;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.KeybindComponent;
import net.kyori.adventure.text.TextComponent;

public class ComponentAdapter {
    public TextComponent text(String content) {
        return Component.text(content);
    }

    public KeybindComponent keybind(String key) {
        return Component.keybind(key);
    }
}
