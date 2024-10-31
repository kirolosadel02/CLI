package com.example.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class RmCommandTest {

    public static void main(String[] args) throws IOException {

        Path tempDir = Files.createTempDirectory("rmCommandTest");
        

        Path testFile = Files.createFile(tempDir.resolve("testFile.txt"));
        System.setProperty("user.dir", tempDir.toString());


        RmCommand rmCommand = new RmCommand();


        System.out.println("Current Directory: " + System.getProperty("user.dir"));
        rmCommand.execute(new String[]{"testFile.txt"});


        if (!Files.exists(testFile)) {
            System.out.println("File successfully deleted.");
        } else {
            System.out.println("File still exists.");
        }

        rmCommand.execute(new String[]{"nonExistentFile.txt"});

        Path testDir = Files.createDirectory(tempDir.resolve("testDir"));
        rmCommand.execute(new String[]{"testDir"});

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
