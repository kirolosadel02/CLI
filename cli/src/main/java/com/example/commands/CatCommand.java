package com.example.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class CatCommand implements Command {
    @Override
    public void execute(String[] args) {
        if (args.length == 0) 
            System.out.println("Please specify a file name.")
            return
        }
        File file = new File(args[0]);
        if (file.exists()) {
            try {
                Files.lines(file.toPath()).forEach(System.out::println);
            } catch (IOException e) {
                System.out.println("Error reading file: " + e.getMessage());
            }
        } else {
            System.out.println("File not found: " + args[0]);
        }
    }
}
