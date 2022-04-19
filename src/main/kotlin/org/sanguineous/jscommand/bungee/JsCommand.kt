package org.sanguineous.jscommand.bungee

import net.md_5.bungee.api.plugin.Plugin
import org.sanguineous.jscommand.bungee.command.JavascriptCommand
import org.sanguineous.jscommand.bungee.command.ReloadCommand
import java.io.File
import java.util.*

class JsCommand : Plugin() {
    private var commands = mutableListOf<JavascriptCommand>()
    val playerData = HashMap<String, Any>()

    override fun onEnable() {
        loadCommands()
        proxy.pluginManager.registerCommand(this, ReloadCommand(this))
    }

    fun loadCommands() {
        if (!dataFolder.exists())
            dataFolder.mkdir()
        val dir = File(dataFolder, "scripts")
        if (!dir.exists())
            dir.mkdir()
        val files = dir.listFiles()
        for (command in commands) {
            proxy.pluginManager.unregisterCommand(command)
        }
        commands.clear()
        for (file in files) {
            if (!file.isFile)
                return
            val fileName = file.name.substring(0, file.name.indexOf(".")).toLowerCase()
            if (proxy.pluginManager.isExecutableCommand(fileName, proxy.console)) {
                println("Found command ${fileName}, registering anyways")
            }
            val contents = StringBuilder()
            val scanner = Scanner(file)
            while (scanner.hasNextLine()) {
                contents.append("${scanner.nextLine()}\n")
            }
            val command = JavascriptCommand(this, fileName, contents.toString())
            proxy.pluginManager.registerCommand(this, command)
            commands.add(command)
        }
    }
}