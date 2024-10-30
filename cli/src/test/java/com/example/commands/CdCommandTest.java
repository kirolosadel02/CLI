package com.example.commands;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class CdCommandTest extends TestCase {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    public CdCommandTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(CdCommandTest.class);
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

    public void testCdCommand() {
        CdCommand cdCommand = new CdCommand();
        cdCommand.execute(new String[] { ".." });
        String output = outContent.toString();
        assertTrue(output.contains("Directory changed to"));
    }

    public void testCdToRoot() {
        CdCommand cdCommand = new CdCommand();
        cdCommand.execute(new String[] {});
        String output = outContent.toString();
        assertTrue(output.contains("Directory changed to"));
    }

    public void testCdToInvalidDirectory() {
        CdCommand cdCommand = new CdCommand();
        cdCommand.execute(new String[] { "invalid" });
        String output = outContent.toString();
        assertTrue(output.contains("Directory not found"));
    }

    public void testCdToNonDirectory() {
        CdCommand cdCommand = new CdCommand();
        cdCommand.execute(new String[] { "testfile.txt" });
        String output = outContent.toString();
        assertTrue(output.contains("Directory not found"));
    }

    public void testCdToRelativeDirectory() {
        CdCommand cdCommand = new CdCommand();
        cdCommand.execute(new String[] { "src" });
        String output = outContent.toString();
        assertTrue(output.contains("Directory changed to"));
    }

    public void testCdToParentDirectory() {
        CdCommand cdCommand = new CdCommand();
        cdCommand.execute(new String[] { ".." });
        String output = outContent.toString();
        assertTrue(output.contains("Directory changed to"));
    }
}
