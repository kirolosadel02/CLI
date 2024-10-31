
package com.example.commands;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;

public class RedirectCommand implements Command {
    private final boolean append;

    public RedirectCommand(boolean append) {
        this.append = append;
    }

    @Override
    public void execute(String[] args) {
        if (args.length < 2) {
            System.out.println("Please specify a target file and a source file.");
            return;
        }

        String targetFileName = args[0];
        String sourceFileName = args[1];

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(targetFileName, append));
             LineNumberReader reader = new LineNumberReader(new FileReader(sourceFileName))) {
            
            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line);
                writer.newLine();
            }
            System.out.println("Content redirected from " + sourceFileName + " to " + targetFileName);
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }
}