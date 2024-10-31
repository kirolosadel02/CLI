package com.example.commands;


import java.io.File;

public class CdCommand implements Command {
    @Override
    public void execute(String[] args) {
        if (args.length == 0) {
            String rootDirectory = getRootDirectory();
            System.setProperty("user.dir", rootDirectory);
            System.out.println("Directory changed to " + rootDirectory);
        } else if (args[0].equals("..")) {
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
            String dirPath = args[0];
            File directory = new File(dirPath);

            if (directory.isDirectory() && directory.exists() && args[0] != "...") {
                System.setProperty("user.dir", directory.getAbsolutePath());
                System.out.println("Directory changed to " + directory.getAbsolutePath());
            } else {

                File relativeDirectory = new File(System.getProperty("user.dir"), dirPath);
                if (relativeDirectory.isDirectory() && relativeDirectory.exists() && args[0] != "...") {
                    System.setProperty("user.dir", relativeDirectory.getAbsolutePath());
                    System.out.println("Directory changed to " + relativeDirectory.getAbsolutePath());
                } else {
                    System.out.println("Directory not found: " + dirPath);
                }
            }
        }
    }

    private String getRootDirectory() {
        String root = "/";
        String os = System.getProperty("os.name").toLowerCase();

        if (os.contains("win")) {
            root = "C:\\";
        }

        return root;
    }
}
