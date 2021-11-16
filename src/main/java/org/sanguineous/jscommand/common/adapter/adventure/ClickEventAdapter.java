package org.sanguineous.jscommand.common.adapter.adventure;

import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.util.Index;

import java.net.URL;

public class ClickEventAdapter {
    public static ClickEvent openUrl(String url) {
        return ClickEvent.openUrl(url);
    }

    public static ClickEvent openUrl(URL url) {
        return ClickEvent.openUrl(url);
    }

    public static ClickEvent openFile(String file) {
        return ClickEvent.openFile(file);
    }

    public static ClickEvent runCommand(String command) {
        return ClickEvent.runCommand(command);
    }

    public static ClickEvent suggestCommand(String command) {
        return ClickEvent.suggestCommand(command);
    }

    public static ClickEvent changePage(String page) {
        return ClickEvent.changePage(page);
    }

    public static ClickEvent changePage(int page) {
        return ClickEvent.changePage(page);
    }

    public static ClickEvent copyToClipboard(String text) {
        return ClickEvent.copyToClipboard(text);
    }

    public static ClickEvent clickEvent(ClickEvent.Action action, String value) {
        return ClickEvent.clickEvent(action, value);
    }
}
