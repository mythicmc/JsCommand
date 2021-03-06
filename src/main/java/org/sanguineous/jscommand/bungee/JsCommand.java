package org.sanguineous.jscommand.bungee;

import net.md_5.bungee.api.plugin.Plugin;
import org.sanguineous.jscommand.bungee.command.JavascriptCommand;
import org.sanguineous.jscommand.bungee.command.ReloadCommand;
import org.sanguineous.jscommand.bungee.listener.PlayerLeaveListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class JsCommand extends Plugin {
    private final HashMap<String, Object> playerData = new HashMap<>();
    private final HashMap<String, JavascriptCommand> commands = new HashMap<>();

    @Override
    public void onEnable() {
        System.setProperty("polyglot.engine.WarnInterpreterOnly", "false");
        try {
            loadCommands();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        getProxy().getPluginManager().registerCommand(this, new ReloadCommand(this));
        getProxy().getPluginManager().registerListener(this, new PlayerLeaveListener(this));
    }

    public void loadCommands() throws FileNotFoundException {
        if (!getDataFolder().exists())
            getDataFolder().mkdir();
        File dir = new File(getDataFolder(), "commands");
        if (!dir.exists())
            dir.mkdir();
        File[] files = dir.listFiles();
        for (JavascriptCommand command : commands.values()) {
            getProxy().getPluginManager().unregisterCommand(command);
        }
        commands.clear();
        for (File file : files) {
            if (!file.isFile())
                return;
            String fileName = file.getName().substring(0, file.getName().indexOf(".")).toLowerCase();
            if (getProxy().getPluginManager().isExecutableCommand(fileName, getProxy().getConsole()))
                System.out.println("Found command " + fileName + ", registering anyways");
            StringBuilder contents = new StringBuilder();
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine())
                contents.append(scanner.nextLine()).append("\n");
            JavascriptCommand command = new JavascriptCommand(this, fileName, contents.toString());
            getProxy().getPluginManager().registerCommand(this, command);
            commands.put(fileName, command);
        }
    }

    public HashMap<String, Object> getPlayerData() {
        return playerData;
    }
}
