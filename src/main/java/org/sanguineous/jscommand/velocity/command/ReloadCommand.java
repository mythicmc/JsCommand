package org.sanguineous.jscommand.velocity.command;

import com.velocitypowered.api.command.SimpleCommand;
import net.kyori.adventure.text.Component;
import org.sanguineous.jscommand.velocity.JsCommand;

import java.io.FileNotFoundException;

public class ReloadCommand implements SimpleCommand {
    private JsCommand plugin;

    public ReloadCommand(JsCommand plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean hasPermission(Invocation invocation) {
        return true;
    }

    @Override
    public void execute(Invocation invocation) {
        String[] args = invocation.arguments();
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("reload") &&
                    invocation.source().hasPermission("jscommand.reload")) {
                try {
                    plugin.loadCommands();
                    invocation.source().sendMessage(Component.text("Successfully reload commands"));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            if (args[0].equalsIgnoreCase("resetplayerdata") &&
                    invocation.source().hasPermission("jscommand.reload")) {
                plugin.getPlayerData().clear();
                invocation.source().sendMessage(Component.text("Successfully reset playerData"));
            }
        }
    }
}
