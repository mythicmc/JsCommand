package org.sanguineous.jscommand.common.adapter.adventure;

import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.util.Index;

public class NamedTextColorAdapter {
    public static final NamedTextColor BLACK = NamedTextColor.BLACK;
    public static final NamedTextColor DARK_BLUE = NamedTextColor.DARK_BLUE;
    public static final NamedTextColor DARK_GREEN = NamedTextColor.DARK_GREEN;
    public static final NamedTextColor DARK_AQUA = NamedTextColor.DARK_AQUA;
    public static final NamedTextColor DARK_RED = NamedTextColor.DARK_RED;
    public static final NamedTextColor DARK_PURPLE = NamedTextColor.DARK_PURPLE;
    public static final NamedTextColor GOLD = NamedTextColor.GOLD;
    public static final NamedTextColor GRAY = NamedTextColor.GRAY;
    public static final NamedTextColor DARK_GRAY = NamedTextColor.DARK_GRAY;
    public static final NamedTextColor BLUE = NamedTextColor.BLUE;
    public static final NamedTextColor GREEN = NamedTextColor.GREEN;
    public static final NamedTextColor AQUA = NamedTextColor.AQUA;
    public static final NamedTextColor RED = NamedTextColor.RED;
    public static final NamedTextColor LIGHT_PURPLE = NamedTextColor.LIGHT_PURPLE;
    public static final NamedTextColor YELLOW = NamedTextColor.YELLOW;
    public static final NamedTextColor WHITE = NamedTextColor.WHITE;

    public static final Index<String, NamedTextColor> NAMES = NamedTextColor.NAMES;

    public static NamedTextColor ofExact(int value) {
        return NamedTextColor.ofExact(value);
    }

    public static NamedTextColor nearestTo(TextColor any) {
        return NamedTextColor.nearestTo(any);
    }
}
