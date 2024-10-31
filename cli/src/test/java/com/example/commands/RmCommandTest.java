package com.example.commands;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

public class RmCommandTest extends TestCase {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    public RmCommandTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(RmCommandTest.class);
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

    public void testNoFileNameSpecified() {
        RmCommand rmCommand = new RmCommand();
        rmCommand.execute(new String[] {});

        String output = outContent.toString().trim();
        assertTrue(output.contains("Please specify a file name."));
    }

    public void testFileNotFound() {
        RmCommand rmCommand = new RmCommand();
        rmCommand.execute(new String[] { "nonexistentfile.txt" });

        String output = outContent.toString().trim();
        assertTrue(output.contains("File not found: nonexistentfile.txt"));
    }

    public void testDeleteExistingFile() throws IOException {
        File tempFile = File.createTempFile("tempfile", ".txt");
        tempFile.deleteOnExit();

        System.setOut(originalOut);
        System.out.println("Temporary file path: " + tempFile.getAbsolutePath());
        System.setOut(new PrintStream(outContent));

        RmCommand rmCommand = new RmCommand();
        rmCommand.execute(new String[] { tempFile.getName() });

        String output = outContent.toString().trim();
        assertTrue(output.contains("Deleted file: " + tempFile.getName()));

        assertFalse(tempFile.exists());
    }

    public void testDeleteDirectoryInsteadOfFile() throws IOException {
        File tempDirectory = new File(System.getProperty("user.dir"), "tempDir");
        tempDirectory.mkdir();
        tempDirectory.deleteOnExit();

        RmCommand rmCommand = new RmCommand();
        rmCommand.execute(new String[] { tempDirectory.getName() });

        String output = outContent.toString().trim();
        assertTrue(output.contains("Specified path is a directory, not a file: " + tempDirectory.getName()));

        assertTrue(tempDirectory.exists());
    }

    public void testFailedDeletion() throws IOException {
        File readOnlyFile = File.createTempFile("readonlyfile", ".txt");
        readOnlyFile.setReadOnly();
        readOnlyFile.deleteOnExit();

        RmCommand rmCommand = new RmCommand();
        rmCommand.execute(new String[] { readOnlyFile.getName() });

        String output = outContent.toString().trim();
        assertTrue(output.contains("Failed to delete file: " + readOnlyFile.getName()));

        readOnlyFile.setWritable(true);
    }
}
