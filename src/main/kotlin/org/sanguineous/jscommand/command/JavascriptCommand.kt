package org.sanguineous.jscommand.command

import net.md_5.bungee.api.CommandSender
import net.md_5.bungee.api.connection.ProxiedPlayer
import net.md_5.bungee.api.plugin.Command
import org.mozilla.javascript.engine.RhinoScriptEngineFactory
import org.sanguineous.jscommand.JsCommand
import javax.script.ScriptEngineManager

class JavascriptCommand(private val plugin: JsCommand, fileName: String, private val contents: String)
    : Command(fileName, "jscommand.command.${fileName}") {
    override fun execute(sender: CommandSender, args: Array<String>) {
        val manager = ScriptEngineManager()
        manager.registerEngineName("rhino", RhinoScriptEngineFactory())
        val engine = manager.getEngineByName("rhino")
        engine.put("sender", sender)
        engine.put("plugin", plugin)
        engine.put("args", args)
        engine.put("isConsole", sender !is ProxiedPlayer)
        engine.put("proxy", plugin.proxy)
        engine.eval(contents)
    }
}