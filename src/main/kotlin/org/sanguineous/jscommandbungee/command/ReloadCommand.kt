package org.sanguineous.jscommandbungee.command

import net.md_5.bungee.api.CommandSender
import net.md_5.bungee.api.chat.ComponentBuilder
import net.md_5.bungee.api.plugin.Command
import org.sanguineous.jscommandbungee.JsCommand

class ReloadCommand(private val plugin: JsCommand) : Command("bjsc", "jscommand.reload") {
    override fun execute(sender: CommandSender?, args: Array<String>?) {
        if (args?.get(0) == "reload") {
            plugin.loadCommands()
            sender!!.sendMessage(*ComponentBuilder("Successfully reloaded commands").create())
        }
    }
}