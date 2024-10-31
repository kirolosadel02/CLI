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
        File dir = new File("testDir");
        if (dir.exists()) {
            dir.delete();
        }

        System.setOut(originalOut);
        System.out.println("Current working directory: " + System.getProperty("user.dir"));
        System.setOut(new PrintStream(outContent));

        MkdirCommand mkdirCommand = new MkdirCommand();
        mkdirCommand.execute(new String[] { "testDir" });
        String output = outContent.toString();

        System.setOut(originalOut);
        System.out.println("Command output: " + output);
        System.out.println("Directory exists: " + dir.exists());
        System.out.println("Directory is directory: " + dir.isDirectory());
        System.setOut(new PrintStream(outContent));

        assertTrue(dir.exists() && dir.isDirectory());

        assertTrue(output.contains("Directory created: testDir"));

        if (dir.exists()) {
            dir.delete();
        }
    }
}
