package org.sanguineous.jscommand.velocity.command;

import com.velocitypowered.api.command.SimpleCommand;
import org.mozilla.javascript.engine.RhinoScriptEngineFactory;
import org.sanguineous.jscommand.common.adapter.adventure.*;
import org.sanguineous.jscommand.velocity.JsCommand;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.List;
import java.util.function.Consumer;

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
        return true;
    }

    @Override
    public void execute(Invocation invocation) {
        if (!invocation.source().hasPermission("jscommand.command."+name))
            return;

        ScriptEngineManager manager = new ScriptEngineManager();
        manager.registerEngineName("rhino", new RhinoScriptEngineFactory());
        ScriptEngine engine = manager.getEngineByName("rhino");
        engine.put("sender", invocation.source());
        engine.put("invocation", invocation);
        engine.put("plugin", plugin);
        engine.put("Class", Class.class);
        engine.put("Component", new ComponentAdapter());
        engine.put("NamedTextColor", new NamedTextColorAdapter());
        engine.put("TextColor", new TextColorAdapter());
        engine.put("ClickEvent", new ClickEventAdapter());
        engine.put("HoverEvent", new HoverEventAdapter());

        try {
            engine.eval(contents);
        } catch (ScriptException e) {
            e.printStackTrace();
        }
    }
}
