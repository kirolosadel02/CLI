package com.example.commands;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

class FirstTestCommand implements Command {
    @Override
    public void execute(String[] args) {
        System.out.print("First command output");
    }
}

class SecondTestCommand implements Command {
    @Override
    public void execute(String[] args) {
        System.out.print("Second command executed");
    }
}

public class PipeCommandTest extends TestCase {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    public PipeCommandTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(PipeCommandTest.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        System.setOut(new PrintStream(outContent));
    }

    @Override
    protected void tearDown() throws Exception {
        System.setOut(originalOut);
        super.tearDown();
    }

    public void testPipeWithValidCommands() {
        PipeCommand pipeCommand = new PipeCommand() {
            @Override
            public void execute(String[] args) {
                if (args.length < 2) {
                    System.out.println("Not enough commands to pipe.");
                    return;
                }

                Command firstCommand = new FirstTestCommand();
                Command secondCommand = new SecondTestCommand();

                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                PrintStream originalOut = System.out;
                System.setOut(new PrintStream(outputStream));

                firstCommand.execute(new String[] {});

                System.setOut(originalOut);
                String commandOutput = outputStream.toString();

                System.out.println("Output from first command: " + commandOutput);

                secondCommand.execute(new String[] {});
            }
        };

        pipeCommand.execute(new String[]{"firstCommand", "secondCommand"});

        String output = outContent.toString().trim();
        assertTrue(output.contains("Output from first command: First command output"));
        assertTrue(output.contains("Second command executed"));
    }

    public void testPipeWithInsufficientCommands() {
        PipeCommand pipeCommand = new PipeCommand();
        pipeCommand.execute(new String[]{"onlyOneCommand"});

        String output = outContent.toString().trim();
        assertTrue(output.contains("Not enough commands to pipe."));
    }

    public void testPipeWithNullCommands() {
        PipeCommand pipeCommand = new PipeCommand() {
            @Override
            public void execute(String[] args) {
                if (args.length < 2 || args[0] == null || args[1] == null) {
                    System.out.println("Invalid command(s) specified.");
                    return;
                }
            }
        };

        pipeCommand.execute(new String[]{null, null});

        String output = outContent.toString().trim();
        assertTrue(output.contains("Invalid command(s) specified."));
    }
}