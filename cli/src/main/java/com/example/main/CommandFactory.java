package com.example.main;

import com.example.commands.*;

public class CommandFactory {
    public static Command getCommand(String commandName, String[] args) {
        switch (commandName) {
            case "pwd":
                return new PwdCommand();
            case "cd":
                return new CdCommand();

            case "ls" : {
                boolean showAll = false;
                boolean reverse = false;
                for (String arg : args) {
                    if (arg.equals("-a")) {
                        showAll = true;
                    } else if (arg.equals("-r")) {
                        reverse = true;
                    }
                }
                return new LsCommand(showAll, reverse);
            }
            case "mkdir":
            return new MkdirCommand();
        case "rmdir":
            return new RmdirCommand();
        case "touch":
            return new TouchCommand();
        case "mv":
            return new MvCommand();
        case "rm":
            return new RmCommand();
        case "cat":
            return new CatCommand();
        case ">":
            return new RedirectCommand(false);
        case ">>":
            return new RedirectCommand(true);
        case "|":
            return new PipeCommand();
        case "exit":
            return new ExitCommand();
        case "help":
            return new HelpCommand();
        default:
            return null;
        }
    }
}