package com.example.commands;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.File;

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
        // Ensure the directory does not exist before the test
        File dir = new File("testDir");
        if (dir.exists()) {
            dir.delete();
        }

        // Debug output: Current working directory
        System.setOut(originalOut);
        System.out.println("Current working directory: " + System.getProperty("user.dir"));
        System.setOut(new PrintStream(outContent));

        MkdirCommand mkdirCommand = new MkdirCommand();
        mkdirCommand.execute(new String[] { "testDir" });
        String output = outContent.toString();

        // Debug output
        System.setOut(originalOut);
        System.out.println("Command output: " + output);
        System.out.println("Directory exists: " + dir.exists());
        System.out.println("Directory is directory: " + dir.isDirectory());
        System.setOut(new PrintStream(outContent));

        // Check if directory was created
        assertTrue(dir.exists() && dir.isDirectory());

        assertTrue(output.contains("Directory created: testDir"));

        // Clean up
        if (dir.exists()) {
            dir.delete();
        }
    }

    // Add more tests for other scenarios
}