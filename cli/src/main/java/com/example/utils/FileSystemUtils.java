package com.example.utils;

import java.io.File;

public class FileSystemUtils {
    public static void listDirectory(String directoryPath, String[] options) {
        File directory = new File(directoryPath);
        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    System.out.println(file.getName());
                }
            } else {
                System.out.println("Error reading directory.");
            }
        } else {
            System.out.println("Not a directory: " + directoryPath);
        }
    }
}
