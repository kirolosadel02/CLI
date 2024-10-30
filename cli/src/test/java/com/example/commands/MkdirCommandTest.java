package com.example.commands;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class MkdirCommandTest extends TestCase {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    public MkdirCommandTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(MkdirCommandTest.class);
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

    public void testMkdirCommand() {
        MkdirCommand mkdirCommand = new MkdirCommand();
        mkdirCommand.execute(new String[] { "testDir" });
        String output = outContent.toString();
        assertTrue(output.contains("Directory created: testDir"));
    }

    // Add more tests for other scenarios
}