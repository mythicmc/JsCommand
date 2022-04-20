package org.sanguineous.jscommand.bungee.command;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.plugin.Command;
import org.sanguineous.jscommand.bungee.JsCommand;

import java.io.FileNotFoundException;

public class ReloadCommand extends Command {
    private JsCommand plugin;

    public ReloadCommand(JsCommand plugin) {
        super("bjsc", "jscommand.reload");
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            try {
                plugin.loadCommands();
                sender.sendMessage(new ComponentBuilder("Successfully reloaded commands").create());
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("reload") &&
                    sender.hasPermission("jscommand.reload")) {
                try {
                    plugin.loadCommands();
                    sender.sendMessage(new ComponentBuilder("Successfully reload commands").create());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            if (args[0].equalsIgnoreCase("resetplayerdata") &&
                    sender.hasPermission("jscommand.reload")) {
                plugin.getPlayerData().clear();
                sender.sendMessage(new ComponentBuilder("Successfully reset playerData").create());
            }
        }
    }
}
