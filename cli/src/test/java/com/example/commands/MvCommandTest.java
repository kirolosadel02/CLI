package com.example.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class MvCommandTest {

    public static void main(String[] args) throws IOException {
        MvCommand mvCommand = new MvCommand();
        Path tempDir = Files.createTempDirectory("mvCommandTest");
        testMoveFileToAnotherDirectory(mvCommand, tempDir);
        testMoveFileRename(mvCommand, tempDir);
        testSourceFileDoesNotExist(mvCommand, tempDir);
        testMoveToNonDirectory(mvCommand, tempDir);
        deleteDirectory(tempDir.toFile());
    }

    private static void testMoveFileToAnotherDirectory(MvCommand mvCommand, Path tempDir) throws IOException {
        Path sourceFile = Files.createFile(tempDir.resolve("source.txt"));
        Path destinationDir = Files.createDirectory(tempDir.resolve("destination"));
        mvCommand.execute(new String[]{"source.txt", "destination"});
        boolean sourceExists = Files.exists(sourceFile);
        boolean destinationExists = Files.exists(destinationDir.resolve("source.txt"));
        System.out.println("Test Move File To Another Directory: " + (!sourceExists && destinationExists));
    }

    private static void testMoveFileRename(MvCommand mvCommand, Path tempDir) throws IOException {
        Path sourceFile = Files.createFile(tempDir.resolve("source.txt"));
        mvCommand.execute(new String[]{"source.txt", "renamed.txt"});
        boolean sourceExists = Files.exists(sourceFile);
        boolean renamedExists = Files.exists(tempDir.resolve("renamed.txt"));
        System.out.println("Test Move File Rename: " + (!sourceExists && renamedExists));
    }

    private static void testSourceFileDoesNotExist(MvCommand mvCommand, Path tempDir) {
        mvCommand.execute(new String[]{"nonexistent.txt", "destination"});
        System.out.println("Test Source File Does Not Exist: Check console for output.");
    }

    private static void testMoveToNonDirectory(MvCommand mvCommand, Path tempDir) throws IOException {
        Path sourceFile = Files.createFile(tempDir.resolve("source.txt"));
        mvCommand.execute(new String[]{"source.txt", "destination.txt"});
        boolean sourceExists = Files.exists(sourceFile);
        boolean destinationExists = Files.exists(tempDir.resolve("destination.txt"));
        System.out.println("Test Move To Non-Directory: " + (!sourceExists && destinationExists));
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
