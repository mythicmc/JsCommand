package org.sanguineous.jscommand.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.sanguineous.jscommand.JsCommand;

public class PlayerLeaveListener implements Listener {
    private JsCommand plugin;

    public PlayerLeaveListener(JsCommand plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        plugin.getPlayerData().remove(event.getPlayer().getName());
    }
}
