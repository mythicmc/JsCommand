package org.sanguineous.jscommand.command

import net.md_5.bungee.api.CommandSender
import net.md_5.bungee.api.chat.ComponentBuilder
import net.md_5.bungee.api.plugin.Command
import org.sanguineous.jscommand.JsCommand

class ReloadCommand(private val plugin: JsCommand) : Command("jscommand") {
    override fun execute(sender: CommandSender?, args: Array<String>?) {
        if (args?.get(0) == "reload" && sender!!.hasPermission("jscommand.reload")) {
            sender!!.sendMessage(*ComponentBuilder("Successfully reloaded commands").create())
            plugin.loadCommands()
        }
    }
}