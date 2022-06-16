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
    private final String contents;
    private final JsCommand plugin;
    private HandlerList handlerList;

    public JavascriptListener(String contents, JsCommand plugin) {
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
        try {
            this.handlerList = (HandlerList) Class.forName(contents.substring(2, contents.indexOf("\n")).strip())
                    .getMethod("getHandlerList").invoke(null);
            handlerList.register(new RegisteredListener(this, (listener, event) -> execute(event),
                    EventPriority.NORMAL, plugin, false));
        } catch (Exception e) {
            System.out.println();
        }
    }

    public void unregister() {
        handlerList.unregister(this);
    }
}
