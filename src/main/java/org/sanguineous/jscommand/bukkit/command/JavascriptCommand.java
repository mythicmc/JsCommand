package org.sanguineous.jscommand.bukkit.command;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.HostAccess;
import org.graalvm.polyglot.Value;
import org.sanguineous.jscommand.bukkit.JsCommand;

import java.awt.*;

public class JavascriptCommand extends BukkitCommand {
    private final String name;
    private final String contents;
    private final JsCommand plugin;

    public JavascriptCommand(String name, String contents, JsCommand plugin) {
        super(name);
        this.name = name;
        this.contents = contents;
        this.plugin = plugin;
        this.setName(name);
        this.setPermission("jscommand.command." + name);
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (!sender.hasPermission("jscommand.command."+name)) {
            sender.sendMessage(Component
                    .text("I'm sorry, but you do not have permission to perform this command."
                            + " Please contact the server administrators if you believe that this is in error.")
                    .color(NamedTextColor.RED));
            return true;
        }
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Thread.currentThread().setContextClassLoader(Context.class.getClassLoader());
        try (Context context = Context.newBuilder("js")
                .allowHostAccess(HostAccess.ALL)
                .allowHostClassLookup(classname -> true)
                .build()) {
            Value value = context.getBindings("js");
            value.putMember("sender", sender);
            value.putMember("plugin", plugin);
            value.putMember("args", args);
            context.eval("js", contents);
        } finally {
            Thread.currentThread().setContextClassLoader(classLoader);
        }
        return true;
    }
}
