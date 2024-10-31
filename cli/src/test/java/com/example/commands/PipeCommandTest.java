package com.example.commands;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import com.example.main.CommandFactory;
import static org.mockito.Mockito.*;

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
        Command mockFirstCommand = mock(Command.class);
        Command mockSecondCommand = mock(Command.class);

        try (var commandFactoryMock = mockStatic(CommandFactory.class)) {
            commandFactoryMock.when(() -> CommandFactory.getCommand("firstCommand", new String[] {}))
                    .thenReturn(mockFirstCommand);
            commandFactoryMock.when(() -> CommandFactory.getCommand("secondCommand", new String[] {}))
                    .thenReturn(mockSecondCommand);

            doAnswer(invocation -> {
                System.out.print("First command output");
                return null;
            }).when(mockFirstCommand).execute(any());

            PipeCommand pipeCommand = new PipeCommand();
            pipeCommand.execute(new String[]{"firstCommand", "secondCommand"});

            String output = outContent.toString().trim();
            assertTrue(output.contains("Output from first command: First command output"));
        }
    }

    public void testPipeWithInsufficientCommands() {
        PipeCommand pipeCommand = new PipeCommand();
        pipeCommand.execute(new String[]{"onlyOneCommand"});

        String output = outContent.toString().trim();
        assertTrue(output.contains("Not enough commands to pipe."));
    }

    public void testPipeWithNullCommands() {
        try (var commandFactoryMock = mockStatic(CommandFactory.class)) {
            commandFactoryMock.when(() -> CommandFactory.getCommand("firstCommand", new String[] {})).thenReturn(null);
            commandFactoryMock.when(() -> CommandFactory.getCommand("secondCommand", new String[] {})).thenReturn(null);

            PipeCommand pipeCommand = new PipeCommand();
            pipeCommand.execute(new String[]{"firstCommand", "secondCommand"});

            String output = outContent.toString().trim();
            assertTrue(output.contains("Invalid command(s) specified."));
        }
    }
}
