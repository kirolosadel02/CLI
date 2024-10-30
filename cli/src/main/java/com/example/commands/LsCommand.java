package com.example.commands;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;

public class LsCommand implements Command {
    private final boolean showAll;
    private final boolean reverse;

    // Constructor for flags
    public LsCommand(boolean showAll, boolean reverse) {
        this.showAll = showAll;
        this.reverse = reverse;
    }

    @Override
    public void execute(String[] args) {
        File currentDir = new File(System.getProperty("user.dir"));
        File[] files = currentDir.listFiles();

        if (files != null) {
            // Sort files
            if (reverse) {
                Arrays.sort(files, Comparator.reverseOrder());
            } else {
                Arrays.sort(files);
            }

            for (File file : files) {
                if (showAll || !file.isHidden()) {
                    System.out.println(file.getName());
                }
            }
        } else {
            System.out.println("Could not list files.");
        }
    }
}