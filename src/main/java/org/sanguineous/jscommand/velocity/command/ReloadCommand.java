package org.sanguineous.jscommand.velocity.command;

import com.velocitypowered.api.command.SimpleCommand;
import net.kyori.adventure.text.Component;
import org.sanguineous.jscommand.velocity.JsCommand;

import java.io.FileNotFoundException;

public class ReloadCommand implements SimpleCommand {
    private final JsCommand plugin;

    public ReloadCommand(JsCommand plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean hasPermission(Invocation invocation) {
        return invocation.source().hasPermission("jscommand.reload");
    }

    @Override
    public void execute(Invocation invocation) {
        String[] args = invocation.arguments();
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("reload")) {
                try {
                    plugin.loadCommands();
                    invocation.source().sendMessage(Component.text("Successfully reloaded commands"));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            if (args[0].equalsIgnoreCase("resetplayerdata")) {
                plugin.getPlayerData().clear();
                invocation.source().sendMessage(Component.text("Successfully reset playerData"));
            }
        }
    }
}
