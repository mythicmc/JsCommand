package org.sanguineous.jscommand.bungee.listener

import net.md_5.bungee.api.event.PlayerDisconnectEvent
import net.md_5.bungee.api.plugin.Listener
import net.md_5.bungee.event.EventHandler
import org.sanguineous.jscommand.bungee.JsCommand

class PlayerLeaveListener(private val plugin: JsCommand) : Listener {
    @EventHandler
    fun onPlayerLeave(event: PlayerDisconnectEvent) {
        plugin.playerData.remove(event.player.name)
    }
}
