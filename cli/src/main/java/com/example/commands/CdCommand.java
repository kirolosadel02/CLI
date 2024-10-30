package com.example.commands;

import java.io.File;

public class CdCommand implements Command {
    @Override
    public void execute(String[] args) {
        if (args.length == 0) {
            // Change to the root directory
            String rootDirectory = getRootDirectory();
            System.setProperty("user.dir", rootDirectory);
            System.out.println("Directory changed to " + rootDirectory);
        } else if (args[0].equals("..")) {
            // Change to the parent directory
            String currentDir = System.getProperty("user.dir");
            File currentDirectory = new File(currentDir);
            File parentDirectory = currentDirectory.getParentFile();

            if (parentDirectory != null) {
                System.setProperty("user.dir", parentDirectory.getAbsolutePath());
                System.out.println("Directory changed to " + parentDirectory.getAbsolutePath());
            } else {
                System.out.println("Already at the root directory.");
            }
        } else {
            // Try to change to the specified directory
            String dirPath = args[0];
            File directory = new File(dirPath);

            // Check if the directory is a valid absolute or relative path
            if (directory.isDirectory() && directory.exists()) {
                System.setProperty("user.dir", directory.getAbsolutePath());
                System.out.println("Directory changed to " + directory.getAbsolutePath());
            } else {
                // Try to interpret the directory name as a relative path
                File relativeDirectory = new File(System.getProperty("user.dir"), dirPath);
                if (relativeDirectory.isDirectory() && relativeDirectory.exists()) {
                    System.setProperty("user.dir", relativeDirectory.getAbsolutePath());
                    System.out.println("Directory changed to " + relativeDirectory.getAbsolutePath());
                } else {
                    System.out.println("Directory not found: " + dirPath);
                }
            }
        }
    }

    private String getRootDirectory() {
        // Get the root directory based on the operating system
        String root = "/";
        String os = System.getProperty("os.name").toLowerCase();

        if (os.contains("win")) {
            // For Windows, return the C: drive root
            root = "C:\\";
        }

        return root;
    }
}