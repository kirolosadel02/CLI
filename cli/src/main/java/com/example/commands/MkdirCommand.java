package com.example.commands;

import java.io.File;

public class MkdirCommand implements Command {
    @Override
    public void execute(String[] args) {
        if (args.length == 0) {
            System.out.println("Please specify a directory name.");
            return;
        }
        File dir = new File(System.getProperty("user.dir") + File.separator + args[0]);
        if (dir.mkdir()) {
            System.out.println("Directory created: " + dir.getName());
        } else {
            System.out.println("Could not create directory: " + args[0]);
        }
    }
}
