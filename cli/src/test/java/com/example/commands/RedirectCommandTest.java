package com.example.commands;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.*;
import java.nio.file.Files;

public class RedirectCommandTest extends TestCase {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    public RedirectCommandTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(RedirectCommandTest.class);
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

    public void testRedirectCommand_SuccessfulRedirection() throws IOException {
        // Set up source file with content
        File sourceFile = File.createTempFile("sourceFile", ".txt");
        sourceFile.deleteOnExit();
        Files.write(sourceFile.toPath(), "This is a test.".getBytes());

        File targetFile = File.createTempFile("targetFile", ".txt");
        targetFile.deleteOnExit();

        RedirectCommand redirectCommand = new RedirectCommand(false); // Not appending
        redirectCommand.execute(new String[]{targetFile.getAbsolutePath(), sourceFile.getAbsolutePath()});

        String output = outContent.toString().trim();
        assertTrue(output.contains("Content redirected from " + sourceFile.getAbsolutePath() + " to " + targetFile.getAbsolutePath()));

        String targetContent = new String(Files.readAllBytes(targetFile.toPath()));
        assertEquals("This is a test.\n", targetContent);
    }

    public void testRedirectCommand_AppendContent() throws IOException {
        File sourceFile = File.createTempFile("sourceFile", ".txt");
        sourceFile.deleteOnExit();
        Files.write(sourceFile.toPath(), "First line.".getBytes());
        File targetFile = File.createTempFile("targetFile", ".txt");
        targetFile.deleteOnExit();
        Files.write(targetFile.toPath(), "Initial content.\n".getBytes());

        RedirectCommand redirectCommand = new RedirectCommand(true); // Appending
        redirectCommand.execute(new String[]{targetFile.getAbsolutePath(), sourceFile.getAbsolutePath()});

        String output = outContent.toString().trim();
        assertTrue(output.contains("Content redirected from " + sourceFile.getAbsolutePath() + " to " + targetFile.getAbsolutePath()));

        String targetContent = new String(Files.readAllBytes(targetFile.toPath()));
        assertEquals("Initial content.\nFirst line.\n", targetContent);
    }

    public void testRedirectCommand_MissingArguments() {
        RedirectCommand redirectCommand = new RedirectCommand(false);
        redirectCommand.execute(new String[]{});

        String output = outContent.toString().trim();
        assertTrue(output.contains("Please specify a target file and a source file."));
    }

    public void testRedirectCommand_NonexistentSourceFile() {
        File targetFile = new File("targetFile.txt");

        RedirectCommand redirectCommand = new RedirectCommand(false);
        redirectCommand.execute(new String[]{targetFile.getAbsolutePath(), "nonexistentSource.txt"});

        String output = outContent.toString().trim();
        assertTrue(output.contains("Error writing to file"));
    }
}
