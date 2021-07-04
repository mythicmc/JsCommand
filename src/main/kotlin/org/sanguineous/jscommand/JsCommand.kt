package org.sanguineous.jscommand

import net.md_5.bungee.api.ProxyServer
import net.md_5.bungee.api.plugin.Plugin
import org.sanguineous.jscommand.command.JavascriptCommand
import java.io.File
import java.util.*

class JsCommand : Plugin() {

    override fun onEnable() {
        loadCommands()
    }

    private fun loadCommands() {
        if (!dataFolder.exists())
            dataFolder.mkdir()
        val dir = File(dataFolder, "scripts")
        if (!dir.exists())
            dir.mkdir()
        val files = dir.listFiles()
        for (file in files) {
            if (!file.isFile)
                return
            val fileName = file.name.substring(0, file.name.indexOf("."))
            val contents = StringBuilder()
            val scanner = Scanner(file)
            while (scanner.hasNextLine()) {
                contents.append("${scanner.nextLine()}\n")
            }
            ProxyServer.getInstance().pluginManager
                    .registerCommand(this, JavascriptCommand(this, fileName, contents.toString()))
        }
    }
}