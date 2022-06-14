package org.sanguineous.jscommand.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import org.sanguineous.jscommand.velocity.command.JavascriptCommand;
import org.sanguineous.jscommand.velocity.command.ReloadCommand;
import org.sanguineous.jscommand.velocity.listener.PlayerLeaveListener;
import org.slf4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Scanner;


@Plugin(id = "jscommand", name = "JsCommand", version = "1.0.0")
public class JsCommand {
    private final File dataFolder;
    private final ProxyServer server;
    private final Logger logger;
    private final HashMap<String, JavascriptCommand> commands = new HashMap<>();
    private final HashMap<String, Object> playerData = new HashMap<>();

    @Inject
    public JsCommand(ProxyServer server, Logger logger, @DataDirectory Path dataPath) {
        dataFolder = dataPath.toFile();
        this.server = server;
        this.logger = logger;
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        System.setProperty("polyglot.engine.WarnInterpreterOnly", "false");
        try {
            loadCommands();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        server.getCommandManager().register("vjsc", new ReloadCommand(this));
        server.getEventManager().register(this, new PlayerLeaveListener(this));
    }

    public void loadCommands() throws FileNotFoundException {
        if (!dataFolder.exists())
            dataFolder.mkdir();
        File dir = new File(dataFolder, "commands");
        if (!dir.exists())
            dir.mkdir();
        File[] files = dir.listFiles();
        for (JavascriptCommand command : commands.values()) {
            server.getCommandManager().unregister(command.getName());
        }
        commands.clear();
        for (File file : files) {
            if (!file.isFile())
                return;
            String fileName = file.getName().substring(0, file.getName().indexOf("."));
            if (server.getCommandManager().hasCommand(fileName)) {
                logger.info("Found command " + fileName + ", registering anyways");
            }
            StringBuilder contents = new StringBuilder();
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                contents.append(scanner.nextLine() + "\n");
            }
            JavascriptCommand command = new JavascriptCommand(fileName, contents.toString(), this);
            server.getCommandManager().register(fileName, command);
            commands.put(fileName, command);
        }
    }

    public ProxyServer getServer() {
        return server;
    }

    public File getDataFolder() {
        return dataFolder;
    }

    public Logger getLogger() {
        return logger;
    }

    public HashMap<String, Object> getPlayerData() {
        return playerData;
    }
}
