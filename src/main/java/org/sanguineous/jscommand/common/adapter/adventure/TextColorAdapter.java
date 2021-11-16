package org.sanguineous.jscommand.common.adapter.adventure;

import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.util.HSVLike;
import net.kyori.adventure.util.RGBLike;
import org.jetbrains.annotations.Range;

public class TextColorAdapter {
    static TextColor color(int value) {
        return TextColor.color(value);
    }

    static TextColor color(RGBLike rgb) {
        return TextColor.color(rgb);
    }

    static TextColor color(HSVLike hsv) {
        return TextColor.color(hsv);
    }

    static TextColor color(@Range(from = 0x0, to = 0xff) int r, @Range(from = 0x0, to = 0xff) int g,
                           @Range(from = 0x0, to = 0xff) int b) {
        return TextColor.color(r, g, b);
    }

    static TextColor color(float r, float g, float b) {
        return TextColor.color(r, g, b);
    }

    static TextColor fromHexString(String string) {
        return TextColor.fromHexString(string);
    }

    static TextColor fromCSSHexString(String string) {
        return TextColor.fromCSSHexString(string);
    }

    static TextColor lerp(float t, RGBLike a, RGBLike b) {
        return TextColor.lerp(t, a, b);
    }
}
