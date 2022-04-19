package org.sanguineous.jscommand.velocity.listener;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import org.sanguineous.jscommand.velocity.JsCommand;

public class PlayerLeaveListener {
    private JsCommand plugin;

    public PlayerLeaveListener(JsCommand plugin) {
        this.plugin = plugin;
    }

    @Subscribe
    public void onPlayerLeave(DisconnectEvent event) {
        plugin.getPlayerData().remove(event.getPlayer().getUsername());
    }
}
