package org.sanguineous.jscommand.velocity.command;

import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.HostAccess;
import org.graalvm.polyglot.Value;
import org.sanguineous.jscommand.velocity.JsCommand;

public class JavascriptCommand implements SimpleCommand {
    private final String name;
    private final String contents;
    private final JsCommand plugin;

    public JavascriptCommand(String name, String contents, JsCommand plugin) {
        this.name = name;
        this.contents = contents;
        this.plugin = plugin;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean hasPermission(Invocation invocation) {
        return invocation.source().hasPermission("jscommand.command."+name);
    }

    @Override
    public void execute(Invocation invocation) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Thread.currentThread().setContextClassLoader(Context.class.getClassLoader());
        try (Context context = Context.newBuilder("js")
                .allowHostAccess(HostAccess.ALL)
                .allowHostClassLookup(classname -> true)
                .build()) {
            Value value = context.getBindings("js");
            value.putMember("sender", invocation.source());
            value.putMember("invocation", invocation);
            value.putMember("plugin", plugin);
            value.putMember("args", invocation.arguments());
            context.eval("js", contents);
        } finally {
            Thread.currentThread().setContextClassLoader(classLoader);
        }
    }
}
