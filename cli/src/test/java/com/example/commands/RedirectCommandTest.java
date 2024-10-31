package com.example.commands;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class RedirectCommandTest {

    public static void main(String[] args) throws IOException {
        Path tempDir = Files.createTempDirectory("redirectCommandTest");
        
        Path sourceFile = Files.createFile(tempDir.resolve("source.txt"));
        Files.writeString(sourceFile, "Hello, World!\nThis is a test.\n");

        Path targetFile1 = tempDir.resolve("target.txt");
        RedirectCommand redirectCommand = new RedirectCommand(false);
        redirectCommand.execute(new String[]{targetFile1.toString(), sourceFile.toString()});
        checkFileContent(targetFile1, "Hello, World!\nThis is a test.\n");

        Files.writeString(sourceFile, "Appending this line.\n");
        RedirectCommand appendCommand = new RedirectCommand(true);
        redirectCommand = appendCommand;
        redirectCommand.execute(new String[]{targetFile1.toString(), sourceFile.toString()});
        checkFileContent(targetFile1, "Hello, World!\nThis is a test.\nAppending this line.\n");

        deleteDirectory(tempDir.toFile());
    }

    private static void checkFileContent(Path filePath, String expectedContent) throws IOException {
        StringBuilder actualContent = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath.toFile()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                actualContent.append(line).append("\n");
            }
        }

        if (actualContent.toString().equals(expectedContent)) {
            System.out.println("Content verification passed for " + filePath.getFileName());
        } else {
            System.out.println("Content verification failed for " + filePath.getFileName());
            System.out.println("Expected:\n" + expectedContent);
            System.out.println("Actual:\n" + actualContent);
        }
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
