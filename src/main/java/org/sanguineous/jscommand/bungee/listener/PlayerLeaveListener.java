package org.sanguineous.jscommand.bungee.listener;

import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import org.sanguineous.jscommand.bungee.JsCommand;

public class PlayerLeaveListener implements Listener {
    private JsCommand plugin;

    public PlayerLeaveListener(JsCommand plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerLeave(PlayerDisconnectEvent event) {
        plugin.getPlayerData().remove(event.getPlayer().getName());
    }
}
