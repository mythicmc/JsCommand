package org.sanguineous.jscommand.bukkit;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.java.JavaPlugin;
import org.sanguineous.jscommand.bukkit.command.JavascriptCommand;
import org.sanguineous.jscommand.bukkit.command.ReloadCommand;
import org.sanguineous.jscommand.bukkit.listener.JavascriptListener;
import org.sanguineous.jscommand.bukkit.listener.PlayerLeaveListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class JsCommand extends JavaPlugin {
    private final HashMap<String, Object> playerData = new HashMap<>();
    private final HashMap<String, JavascriptCommand> commands = new HashMap<>();
    private final HashMap<String, JavascriptListener> listeners = new HashMap<>();
    private final CommandMap commandMap = Bukkit.getCommandMap();

    @Override
    public void onEnable() {
        System.setProperty("polyglot.engine.WarnInterpreterOnly", "false");
        try {
            loadCommands();
            loadListeners();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        getCommand("jsc").setExecutor(new ReloadCommand(this));
        getServer().getPluginManager().registerEvents(new PlayerLeaveListener(this), this);
    }

    public HashMap<String, Object> getPlayerData() {
        return playerData;
    }

    public void loadCommands() throws FileNotFoundException {
        if (!getDataFolder().exists())
            getDataFolder().mkdir();
        File dir = new File(getDataFolder(), "commands");
        if (!dir.exists())
            dir.mkdir();
        File[] files = dir.listFiles();
        for (JavascriptCommand command : commands.values()) {
            command.unregister(commandMap);
            commandMap.getKnownCommands().remove(command.getName());
        }
        commands.clear();
        for (File file : files) {
            if (!file.isFile())
                return;
            String fileName = file.getName().substring(0, file.getName().indexOf(".")).toLowerCase();
            StringBuilder contents = new StringBuilder();
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                contents.append(scanner.nextLine()).append("\n");
            }
            JavascriptCommand command = new JavascriptCommand(fileName, contents.toString(), this);
            commands.put(fileName, command);
            commandMap.register(fileName, command);
        }
    }

    public void loadListeners() throws FileNotFoundException {
        if (!getDataFolder().exists())
            getDataFolder().mkdir();
        File dir = new File(getDataFolder(), "listeners");
        if (!dir.exists())
            dir.mkdir();
        File[] files = dir.listFiles();
        for (JavascriptListener listener : listeners.values()) {
            listener.unregister();
        }
        listeners.clear();
        for (File file : files) {
            if (!file.isFile())
                return;
            String fileName = file.getName().substring(0, file.getName().indexOf(".")).toLowerCase();
            StringBuilder contents = new StringBuilder();
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                contents.append(scanner.nextLine()).append("\n");
            }
            JavascriptListener listener = new JavascriptListener(contents.toString(), this);
            listener.register();
            listeners.put(fileName, listener);
        }
    }
}
