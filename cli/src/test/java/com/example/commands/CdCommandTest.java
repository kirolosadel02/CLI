package com.example.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class CdCommandTest {

    public static void main(String[] args) throws IOException {
        // Create a temporary directory structure for testing
        Path tempDir = Files.createTempDirectory("cdCommandTest");
        Path subDir = Files.createDirectory(tempDir.resolve("subDir"));
        
        // Current directory is set to the temporary directory
        System.setProperty("user.dir", tempDir.toString());

        // Test changing to a subdirectory
        CdCommand cdCommand = new CdCommand();
        System.out.println("Current Directory: " + System.getProperty("user.dir"));
        cdCommand.execute(new String[]{"subDir"});
        System.out.println("Changed Directory: " + System.getProperty("user.dir"));

        // Test changing to the parent directory
        cdCommand.execute(new String[]{".."});
        System.out.println("Changed Directory: " + System.getProperty("user.dir"));

        // Test changing to the root directory
        cdCommand.execute(new String[]{});
        System.out.println("Changed Directory: " + System.getProperty("user.dir"));

        // Test changing to a non-existent directory
        cdCommand.execute(new String[]{"nonExistentDir"});

        // Clean up temporary directory
        deleteDirectory(tempDir.toFile());
    }

    private static void deleteDirectory(File directory) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteDirectory(file);
                } else {
                    file.delete();
                }
            }
        }
        directory.delete();
    }
}
