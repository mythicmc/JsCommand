package org.sanguineous.jscommand;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.plugin.java.JavaPlugin;
import org.sanguineous.jscommand.command.JavascriptCommand;
import org.sanguineous.jscommand.command.ReloadCommand;
import org.sanguineous.jscommand.listener.PlayerLeaveListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.util.*;

public class JsCommand extends JavaPlugin {
    private final Map<String, Object> playerData = new HashMap<>();
    private final List<JavascriptCommand> commands = new ArrayList<>();

    @Override
    public void onEnable() {
        try {
            loadCommands();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        getCommand("jsc").setExecutor(new ReloadCommand(this));
        getServer().getPluginManager().registerEvents(new PlayerLeaveListener(this), this);
    }

    public Map<String, Object> getPlayerData() {
        return playerData;
    }

    public void loadCommands() throws FileNotFoundException {
        if (!getDataFolder().exists())
            getDataFolder().mkdir();
        File dir = new File(getDataFolder(), "scripts");
        if (!dir.exists())
            dir.mkdir();
        File[] files = dir.listFiles();
        for (JavascriptCommand command : commands) {
            command.unregister(getCommandMap());
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
            commands.add(command);
            getCommandMap().register(fileName, command);
        }
    }

    public CommandMap getCommandMap() {
        CommandMap commandMap = null;
        try {
            Field field = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            field.setAccessible(true);
            commandMap = (CommandMap) field.get(Bukkit.getServer());
        } catch (NoSuchFieldException | IllegalAccessException | IllegalArgumentException
                | SecurityException e) {
            e.printStackTrace();
        }
        return commandMap;
    }

    public void unregisterCommand(Command command) {
        try {
            Field field = getCommandMap().getClass().getDeclaredField("knownCommands");
            field.setAccessible(true);
            @SuppressWarnings("unchecked")
            Map<String, BukkitCommand> commands = (Map<String, BukkitCommand>) field.get(getCommandMap());
            System.out.println(commands.get(command.getName()) instanceof JavascriptCommand);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
