package com.example.commands;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class HelpCommandTest extends TestCase {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    public HelpCommandTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(HelpCommandTest.class);
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

    public void testHelpCommand() {
        HelpCommand helpCommand = new HelpCommand();
        helpCommand.execute(new String[] {});
        String output = outContent.toString();
        assertTrue(output.contains("Available commands:"));
    }

    // Add more tests for other scenarios
}