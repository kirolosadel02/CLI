package com.example.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class LsCommandTest {

    public static void main(String[] args) throws IOException {
        Path tempDir = Files.createTempDirectory("lsCommandTest");
        
        createTestFiles(tempDir);

        System.out.println("Test without flags:");
        LsCommand lsCommand = new LsCommand(false, false);
        lsCommand.execute(new String[]{});

        System.out.println("\nTest with showAll flag:");
        lsCommand = new LsCommand(true, false);
        lsCommand.execute(new String[]{});

        System.out.println("\nTest with reverse flag:");
        lsCommand = new LsCommand(false, true);
        lsCommand.execute(new String[]{});

        System.out.println("\nTest with showAll and reverse flags:");
        lsCommand = new LsCommand(true, true);
        lsCommand.execute(new String[]{});

        deleteDirectory(tempDir.toFile());
    }

    private static void createTestFiles(Path tempDir) throws IOException {
        Files.createFile(tempDir.resolve("file1.txt"));
        Files.createFile(tempDir.resolve("file2.txt"));
        Files.createFile(tempDir.resolve(".hiddenfile"));
        Files.createFile(tempDir.resolve("file3.txt"));
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
