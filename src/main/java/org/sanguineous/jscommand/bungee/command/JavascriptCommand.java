package org.sanguineous.jscommand.bungee.command;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.HostAccess;
import org.graalvm.polyglot.Value;
import org.sanguineous.jscommand.bungee.JsCommand;

public class JavascriptCommand extends Command {
    private final JsCommand plugin;
    private final String contents;

    public JavascriptCommand(JsCommand plugin, String fileName, String contents) {
        super(fileName, "jscommand.command."+fileName);
        this.plugin = plugin;
        this.contents = contents;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Thread.currentThread().setContextClassLoader(Context.class.getClassLoader());
        try (Context context = Context.newBuilder("js")
                .allowHostAccess(HostAccess.ALL)
                .allowHostClassLookup(classname -> true)
                .build()) {
            Value value = context.getBindings("js");
            value.putMember("sender", sender);
            value.putMember("plugin", plugin);
            value.putMember("proxy", plugin.getProxy());
            value.putMember("args", args);
            context.eval("js", contents);
        } finally {
            Thread.currentThread().setContextClassLoader(classLoader);
        }
    }
}
