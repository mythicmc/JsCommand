package org.sanguineous.jscommand.bungee.command;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.HostAccess;
import org.graalvm.polyglot.Value;
import org.sanguineous.jscommand.bungee.JsCommand;

public class JavascriptCommand extends Command {
    private JsCommand plugin;
    private String contents;

    public JavascriptCommand(JsCommand plugin, String fileName, String contents) {
        super(fileName, "jscommand.command."+fileName);
        this.plugin = plugin;
        this.contents = contents;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Context context = Context.newBuilder("js")
                .allowHostAccess(HostAccess.ALL)
                .allowHostClassLookup(className -> true)
                .build();
        Value value = context.getBindings("js");
        value.putMember("sender", sender);
        value.putMember("plugin", plugin);
        value.putMember("proxy", plugin.getProxy());
        value.putMember("args", args);
        value.putMember("isConsole", !(sender instanceof ProxiedPlayer));
        context.eval("js", contents);
    }
}
