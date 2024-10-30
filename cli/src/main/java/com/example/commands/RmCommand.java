package com.example.commands;

import java.io.File;

public class RmCommand implements Command {
    @Override
    public void execute(String[] args) {
        if (args.length == 0) {
            System.out.println("Please specify a file name.");
            return;
        }

        File file = new File(System.getProperty("user.dir"), args[0]);

        if (file.exists()) {
            if (file.isFile()) {
                if (file.delete()) {
                    System.out.println("Deleted file: " + file.getName());
                } else {
                    System.out.println("Failed to delete file: " + file.getName());
                }
            } else {
                System.out.println("Specified path is a directory, not a file: " + file.getName());
            }
        } else {
            System.out.println("File not found: " + file.getName());
        }
    }
}