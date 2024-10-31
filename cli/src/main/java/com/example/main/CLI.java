package com.example.main;

import com.example.commands.*;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

public class CLI {

    public static void main(String[] args) throws IOException {
        CLI cli = new CLI();
        cli.run();
    }

    public void run() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Welcome to the CLI! Type 'exit' to quit.");

        while (true) {
            System.out.print("> ");
            try {
                String input = reader.readLine();
                if (input == null || input.equalsIgnoreCase("exit")) {
                    System.out.println("Exiting...");
                    break;
                }
                executeCommand(input.trim());
            } catch (IOException e) {
                System.err.println("Error reading input: " + e.getMessage());
            }
        }
    }

    private void executeCommand(String input) {
        try {
            if (input.contains("|")) {
                handlePiping(input);
            } else if (input.contains(">")) {
                handleRedirection(input);
            } else {
                executeSingleCommand(input);
            }
        } catch (Exception e) {
            System.err.println("Error executing command: " + e.getMessage());
        }
    }

    private void executeSingleCommand(String input) {
        String[] tokens = input.split(" ");
        Command command = CommandFactory.getCommand(tokens[0], getCommandArgs(tokens));
        if (command != null) {
            command.execute(getCommandArgs(tokens));
        } else {
            System.out.println("Command not recognized: " + tokens[0]);
        }
    }

    private void handleRedirection(String input) throws IOException {
        String[] parts;
        boolean appendMode = false;

        if (input.contains(">>")) {
            parts = input.split(">>");
            appendMode = true;
        } else {
            parts = input.split(">");
        }

        String commandPart = parts[0].trim();
        String fileName = parts[1].trim();

        try (PrintStream fileOut = new PrintStream(new FileOutputStream(fileName, appendMode))) {
            PrintStream originalOut = System.out;
            System.setOut(fileOut);
            executeSingleCommand(commandPart);
            System.setOut(originalOut);
        } catch (FileNotFoundException e) {
            System.err.println("Error redirecting output to file: " + e.getMessage());
        }
    }

    private void handlePiping(String input) throws IOException {
        String[] commands = input.split("\\|");
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        PrintStream originalOut = System.out;
        PrintStream tempOut = new PrintStream(outputStream);

        for (int i = 0; i < commands.length; i++) {
            String command = commands[i].trim();
            String[] tokens = command.split(" ");
            Command cmd = CommandFactory.getCommand(tokens[0], getCommandArgs(tokens));

            if (cmd == null) {
                System.out.println("Command not recognized: " + tokens[0]);
                return;
            }

            // Set System.out to tempOut for intermediate commands
            if (i < commands.length - 1) {
                System.setOut(tempOut);
            } else {
                System.setOut(originalOut);
            }

            cmd.execute(getCommandArgs(tokens));

            if (i < commands.length - 1) {
                outputStream.flush();
                System.setIn(new ByteArrayInputStream(outputStream.toByteArray()));
                outputStream.reset();
            }
        }

        System.setOut(originalOut);
    }

    private String[] getCommandArgs(String[] tokens) {
        if (tokens.length > 1) {
            String[] args = new String[tokens.length - 1];
            System.arraycopy(tokens, 1, args, 0, tokens.length - 1);
            return args;
        }
        return new String[0];
    }
}