package org.sanguineous.jscommand.bukkit.command;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.mozilla.javascript.engine.RhinoScriptEngineFactory;
import org.sanguineous.jscommand.bukkit.JsCommand;

import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

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
        var manager = new ScriptEngineManager();
        manager.registerEngineName("rhino", new RhinoScriptEngineFactory());
        var engine = manager.getEngineByName("rhino");
        engine.put("sender", sender);
        engine.put("plugin", plugin);
        engine.put("Bukkit", Bukkit.class);
        engine.put("Class", Class.class);
        engine.put("args", args);
        engine.put("isConsole", !(sender instanceof Player));
        try {
            engine.eval(contents);
        } catch (ScriptException e) {
            e.printStackTrace();
        }
        return true;
    }
}