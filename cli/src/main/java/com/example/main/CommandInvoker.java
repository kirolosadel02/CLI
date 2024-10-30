package com.example.main;

import com.example.commands.*;

import java.util.HashMap;
import java.util.Map;

public class CommandInvoker {
    private static CommandInvoker instance;
    private final Map<String, Command> commands = new HashMap<>();

    private CommandInvoker() {
        // Register commands with the CommandFactory
        commands.put("pwd", CommandFactory.getCommand("pwd", new String[] {}));
        commands.put("cd", CommandFactory.getCommand("cd", new String[] {}));
        commands.put("ls", CommandFactory.getCommand("ls", new String[] {})); // Default without args
        commands.put("mkdir", CommandFactory.getCommand("mkdir", new String[] {}));
        commands.put("rmdir", CommandFactory.getCommand("rmdir", new String[] {}));
        commands.put("touch", CommandFactory.getCommand("touch", new String[] {}));
        commands.put("mv", CommandFactory.getCommand("mv", new String[] {}));
        commands.put("rm", CommandFactory.getCommand("rm", new String[] {}));
        commands.put("cat", CommandFactory.getCommand("cat", new String[] {}));
        commands.put(">", CommandFactory.getCommand(">", new String[] {})); // Redirect output
        commands.put(">>", CommandFactory.getCommand(">>", new String[] {})); // Append output
        commands.put("|", CommandFactory.getCommand("|", new String[] {})); // Pipe command
        commands.put("exit", CommandFactory.getCommand("exit", new String[] {}));
        commands.put("help", CommandFactory.getCommand("help", new String[] {}));
    }

    public static CommandInvoker getInstance() {
        if (instance == null) {
            instance = new CommandInvoker();
        }
        return instance;
    }

    public void executeCommand(String input) {
        String[] parts = input.split(" ");
        String commandName = parts[0];
        String[] args = new String[parts.length - 1];
        System.arraycopy(parts, 1, args, 0, parts.length - 1);

        // Use CommandFactory to get the command and execute it
        Command command = CommandFactory.getCommand(commandName, args);
        if (command != null) {
            command.execute(args);
        } else {
            System.out.println("Command not found: " + commandName);
        }
    }
}
