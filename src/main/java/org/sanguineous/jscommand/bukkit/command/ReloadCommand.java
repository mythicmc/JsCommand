package org.sanguineous.jscommand.bukkit.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.sanguineous.jscommand.bukkit.JsCommand;

import java.io.FileNotFoundException;

public class ReloadCommand implements CommandExecutor {
    private final JsCommand plugin;

    public ReloadCommand(JsCommand plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("reload")) {
                try {
                    plugin.loadCommands();
                    plugin.loadListeners();
                    sender.sendMessage("Successfully reloaded commands and listeners");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            if (args[0].equalsIgnoreCase("resetplayerdata")) {
                plugin.getPlayerData().clear();
                sender.sendMessage("Successfully reset playerData");
            }
        }
        return true;
    }
}
