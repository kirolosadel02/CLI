package com.example.commands;

import java.io.File;

public class MvCommand implements Command {
    @Override
    public void execute(String[] args) {
        if (args.length < 2) {
            System.out.println("Please specify source and destination.");
            return;
        }

        String currentDir = System.getProperty("user.dir");
        
        File source = new File(currentDir, args[0]);
        File destination = new File(currentDir, args[1]);

        if (!source.exists()) {
            System.out.println("Source file does not exist: " + source.getAbsolutePath());
            return;
        }

        if (destination.isDirectory()) {
            File newFile = new File(destination, source.getName());
            if (source.renameTo(newFile)) {
                System.out.println("Moved " + source.getName() + " to " + destination.getAbsolutePath());
            } else {
                System.out.println("Failed to move " + source.getName() + " to " + destination.getAbsolutePath());
            }
        } else {
            if (source.renameTo(destination)) {
                System.out.println("Renamed " + source.getName() + " to " + destination.getName());
            } else {
                System.out.println("Failed to rename " + source.getName() + " to " + destination.getName());
            }
        }
    }
}
