package org.sanguineous.jscommand.bungee.command;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import org.mozilla.javascript.engine.RhinoScriptEngineFactory;
import org.sanguineous.jscommand.bungee.JsCommand;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

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
        ScriptEngineManager manager = new ScriptEngineManager();
        manager.registerEngineName("rhino", new RhinoScriptEngineFactory());
        ScriptEngine engine = manager.getEngineByName("rhino");
        engine.put("sender", sender);
        engine.put("plugin", plugin);
        engine.put("proxy", plugin.getProxy());
        engine.put("args", args);
        engine.put("isConsole", !(sender instanceof ProxiedPlayer));
        try {
            engine.eval(contents);
        } catch (ScriptException e) {
            e.printStackTrace();
        }
    }
}
