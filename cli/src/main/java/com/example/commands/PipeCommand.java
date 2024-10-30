package com.example.commands;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import com.example.main.CommandFactory;

public class PipeCommand implements Command {
    @Override
    public void execute(String[] args) {
        if (args.length < 2) {
            System.out.println("Not enough commands to pipe.");
            return;
        }

        // First command
        Command firstCommand = CommandFactory.getCommand(args[0], new String[] {});
        // Second command
        Command secondCommand = CommandFactory.getCommand(args[1], new String[] {});

        // Capture output from the first command
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        if (firstCommand != null) {
            firstCommand.execute(new String[] {}); // Execute first command
        }

        // Restore original output
        System.setOut(originalOut);
        String commandOutput = outputStream.toString(); // Get the captured output

        // Now we would pass this output to the second command
        // Here you might want to modify secondCommand to accept input
        // For simplicity, we will just print it out
        System.out.println("Output from first command: " + commandOutput);

        if (secondCommand != null) {
            // If secondCommand can accept input, you might need to modify its execute
            // method
            // For demo purposes, we'll just execute it without any args
            secondCommand.execute(new String[] {});
        }
    }
}