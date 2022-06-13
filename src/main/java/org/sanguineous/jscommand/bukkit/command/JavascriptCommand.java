package org.sanguineous.jscommand.bukkit.command;

import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.HostAccess;
import org.graalvm.polyglot.Value;
import org.sanguineous.jscommand.bukkit.JsCommand;

public class JavascriptCommand extends BukkitCommand {
    private final String contents;
    private final JsCommand plugin;

    public JavascriptCommand(String name, String contents, JsCommand plugin) {
        super(name);
        this.contents = contents;
        this.plugin = plugin;
        this.setName(name);
        this.setPermission("jscommand.command." + name);
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        Context context = Context.newBuilder("js")
                .allowHostAccess(HostAccess.ALL)
                .allowHostClassLookup(className -> true)
                .build();
        Value value = context.getBindings("js");
        value.putMember("sender", sender);
        value.putMember("plugin", plugin);
        value.putMember("args", args);
        value.putMember("isConsole", !(sender instanceof Player));
        context.eval("js", contents);
        return true;
    }
}
