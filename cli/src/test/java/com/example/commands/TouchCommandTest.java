package com.example.commands;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

public class TouchCommandTest extends TestCase {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    public TouchCommandTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(TouchCommandTest.class);
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
        TouchCommand touchCommand = new TouchCommand();
        touchCommand.execute(new String[] {});

        String output = outContent.toString().trim();
        assertTrue(output.contains("Please specify a file name."));
    }

    public void testCreateNewFile() throws IOException {
        String fileName = "newFile.txt";
        File tempFile = new File(System.getProperty("user.dir") + File.separator + fileName);

        // to make sure the file does not exist before running the test
        if (tempFile.exists()) {
            tempFile.delete();
        }

        TouchCommand touchCommand = new TouchCommand();
        touchCommand.execute(new String[] { fileName });

        String output = outContent.toString().trim();
        assertTrue(output.contains("File created: " + fileName));

        assertTrue(tempFile.exists());

        tempFile.delete();
    }

    public void testFileAlreadyExists() throws IOException {
        String fileName = "existingFile.txt";
        File tempFile = new File(System.getProperty("user.dir") + File.separator + fileName);
        tempFile.createNewFile();
        tempFile.deleteOnExit();

        TouchCommand touchCommand = new TouchCommand();
        touchCommand.execute(new String[] { fileName });

        String output = outContent.toString().trim();
        assertTrue(output.contains("File already exists: " + fileName));
    }

    public void testErrorCreatingFile() {
        TouchCommand touchCommand = new TouchCommand();
        touchCommand.execute(new String[] { "/root/someFile.txt" });

        String output = outContent.toString().trim();
        assertTrue(output.contains("Error creating file:"));
    }
}
