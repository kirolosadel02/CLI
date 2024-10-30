package com.example.commands;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class CatCommandTest extends TestCase {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    public CatCommandTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(CatCommandTest.class);
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

    public void testCatCommand() {
        CatCommand catCommand = new CatCommand();
        catCommand.execute(new String[] { "testfile.txt" });
        String output = outContent.toString();
        assertTrue(output.contains("File not found: testfile.txt"));
    }

    public void testNoFileNameSpecified() {
        CatCommand catCommand = new CatCommand();
        catCommand.execute(new String[] {});
        String output = outContent.toString();
        assertTrue(output.contains("Please specify a file name."));
    }

    public void testFileExists() throws IOException {
        // Create a temporary file with some content
        File tempFile = File.createTempFile("testfile", ".txt");
        tempFile.deleteOnExit();
        Files.write(tempFile.toPath(), "Hello, World!".getBytes());

        // Debug output
        System.setOut(originalOut);
        System.out.println("Temporary file path: " + tempFile.getAbsolutePath());
        System.setOut(new PrintStream(outContent));

        CatCommand catCommand = new CatCommand();
        catCommand.execute(new String[] { tempFile.getAbsolutePath() });
        String output = outContent.toString();

        // Debug output
        System.setOut(originalOut);
        System.out.println("Command output: " + output);
        System.setOut(new PrintStream(outContent));

        assertTrue(output.contains("Hello, World!"));
    }

    public void testErrorReadingFile() {
        CatCommand catCommand = new CatCommand();
        catCommand.execute(new String[] { "nonexistentfile.txt" });
        String output = outContent.toString();
        assertTrue(output.contains("File not found: nonexistentfile.txt"));
    }
}
