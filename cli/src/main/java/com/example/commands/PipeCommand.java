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

        Command firstCommand = CommandFactory.getCommand(args[0], new String[] {});

        Command secondCommand = CommandFactory.getCommand(args[1], new String[] {});

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        if (firstCommand != null) {
            firstCommand.execute(new String[] {});
        }

        System.setOut(originalOut);
        String commandOutput = outputStream.toString();

        System.out.println("Output from first command: " + commandOutput);

        if (secondCommand != null) {
            secondCommand.execute(new String[] {});
        }
    }
}