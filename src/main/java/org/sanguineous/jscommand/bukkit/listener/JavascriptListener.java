package org.sanguineous.jscommand.bukkit.listener;

import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredListener;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.HostAccess;
import org.graalvm.polyglot.Value;
import org.sanguineous.jscommand.bukkit.JsCommand;

public class JavascriptListener implements Listener {
    private final String fileName;
    private final String contents;
    private final JsCommand plugin;
    private HandlerList handlerList;

    public JavascriptListener(String fileName, String contents, JsCommand plugin) {
        this.fileName = fileName;
        this.contents = contents;
        this.plugin = plugin;
    }

    public void execute(Event event) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Thread.currentThread().setContextClassLoader(Context.class.getClassLoader());
        try (Context context = Context.newBuilder("js")
                .allowHostAccess(HostAccess.ALL)
                .allowHostClassLookup(classname -> true)
                .build()) {
            Value value = context.getBindings("js");
            value.putMember("event", event);
            value.putMember("plugin", plugin);
            context.eval("js", contents);
        } finally {
            Thread.currentThread().setContextClassLoader(classLoader);
        }
    }

    public void register() {
        String[] split = contents.substring(2, contents.indexOf("\n")).strip().split("\\s+");
        try {
            this.handlerList = (HandlerList) Class.forName(split[0])
                    .getMethod("getHandlerList").invoke(null);
            handlerList.register(new RegisteredListener(this, (listener, event) -> execute(event),
                    split.length == 2 ? EventPriority.valueOf(split[1]) : EventPriority.NORMAL, plugin, false));
            plugin.getListeners().put(fileName, this);
        } catch (Exception e) {
            plugin.getLogger().severe("Failed to register listener " + fileName + " on event "
                    + split[0]);
            e.printStackTrace();
        }
    }

    public void unregister() {
        handlerList.unregister(this);
    }
}
