package com.example.commands;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;

public class RmdirCommandTest extends TestCase {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    public RmdirCommandTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(RmdirCommandTest.class);
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

    public void testNoDirectoryNameSpecified() {
        RmdirCommand rmdirCommand = new RmdirCommand();
        rmdirCommand.execute(new String[] {});

        String output = outContent.toString().trim();
        assertTrue(output.contains("Please specify a directory name."));
    }

    public void testRemoveExistingDirectory() {
        // Create a temporary directory
        File tempDir = new File(System.getProperty("user.dir") + File.separator + "tempDir");
        tempDir.mkdir();
        tempDir.deleteOnExit();

        RmdirCommand rmdirCommand = new RmdirCommand();
        rmdirCommand.execute(new String[] { "tempDir" });

        String output = outContent.toString().trim();
        assertTrue(output.contains("Directory removed: tempDir"));

        assertFalse(tempDir.exists());
    }

    public void testDirectoryDoesNotExist() {
        RmdirCommand rmdirCommand = new RmdirCommand();
        rmdirCommand.execute(new String[] { "nonexistentDir" });

        String output = outContent.toString().trim();
        assertTrue(output.contains("Could not remove directory: nonexistentDir"));
    }

    public void testCannotRemoveNonEmptyDirectory() {
        File tempDir = new File(System.getProperty("user.dir") + File.separator + "nonEmptyDir");
        tempDir.mkdir();
        tempDir.deleteOnExit();

        File tempFile = new File(tempDir, "tempFile.txt");
        try {
            tempFile.createNewFile();
            tempFile.deleteOnExit();
        } catch (Exception e) {
            fail("Failed to create temporary file in non-empty directory.");
        }

        RmdirCommand rmdirCommand = new RmdirCommand();
        rmdirCommand.execute(new String[] { "nonEmptyDir" });

        String output = outContent.toString().trim();
        assertTrue(output.contains("Could not remove directory: nonEmptyDir"));

        assertTrue(tempDir.exists());
    }
}
