package com.example.commands;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class ExitCommandTest extends TestCase {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    public ExitCommandTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(ExitCommandTest.class);
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

    public void testExitCommand() {
        Runnable mockExitAction = new Runnable() {
            @Override
            public void run() {
                // Do nothing
            }
        };

        ExitCommand exitCommand = new ExitCommand(mockExitAction);
        exitCommand.execute(new String[] {});
        String output = outContent.toString();
        assertTrue(output.contains("Exiting CLI..."));
    }
}