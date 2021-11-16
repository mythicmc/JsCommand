package org.sanguineous.jscommand.common.adapter.adventure;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.Keyed;
import net.kyori.adventure.nbt.api.BinaryTagHolder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.text.event.HoverEvent;
import org.jetbrains.annotations.Range;

import java.util.UUID;

public class HoverEventAdapter {
    public static HoverEvent<Component> showText(ComponentLike text) {
        return HoverEvent.showText(text);
    }

    public static HoverEvent<Component> showText(Component text) {
        return HoverEvent.showText(text);
    }

    public static HoverEvent<HoverEvent.ShowItem> showItem(Key item, @Range(from = 0, to = Integer.MAX_VALUE) int count) {
        return HoverEvent.showItem(item, count);
    }

    public static HoverEvent<HoverEvent.ShowItem> showItem(Keyed item, @Range(from = 0, to = Integer.MAX_VALUE) int count) {
        return HoverEvent.showItem(item, count);
    }

    public static HoverEvent<HoverEvent.ShowItem> showItem(Key item, @Range(from = 0, to = Integer.MAX_VALUE) int count,
                                                           BinaryTagHolder nbt) {
        return HoverEvent.showItem(item, count, nbt);
    }

    public static HoverEvent<HoverEvent.ShowItem> showItem(Keyed item, @Range(from = 0, to = Integer.MAX_VALUE) int count,
                                                           BinaryTagHolder nbt) {
        return HoverEvent.showItem(item, count, nbt);
    }

    public static HoverEvent<HoverEvent.ShowItem> showItem(HoverEvent.ShowItem item) {
        return HoverEvent.showItem(item);
    }

    public static HoverEvent<HoverEvent.ShowEntity> showEntity(Key type, UUID id) {
        return HoverEvent.showEntity(type, id);
    }

    public static HoverEvent<HoverEvent.ShowEntity> showEntity(Keyed type, UUID id) {
        return HoverEvent.showEntity(type, id);
    }

    public static HoverEvent<HoverEvent.ShowEntity> showEntity(Key type, UUID id, Component name) {
        return HoverEvent.showEntity(type, id, name);
    }

    public static HoverEvent<HoverEvent.ShowEntity> showEntity(Keyed type, UUID id, Component name) {
        return HoverEvent.showEntity(type, id, name);
    }

    public static HoverEvent<HoverEvent.ShowEntity> showEntity(HoverEvent.ShowEntity entity) {
        return HoverEvent.showEntity(entity);
    }

    public static <V> HoverEvent<V> hoverEvent(HoverEvent.Action<V> action, V value) {
        return HoverEvent.hoverEvent(action, value);
    }
}
