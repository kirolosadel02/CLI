package com.example.commands;

import java.io.File;
import java.io.IOException;

public class TouchCommand implements Command {
    @Override
    public void execute(String[] args) {
        if (args.length == 0) {
            System.out.println("Please specify a file name.");
            return;
        }
        File file = new File(System.getProperty("user.dir") + File.separator + args[0]);
        try {
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            } else {
                System.out.println("File already exists: " + args[0]);
            }
        } catch (IOException e) {
            System.out.println("Error creating file: " + e.getMessage());
        }
    }
}
