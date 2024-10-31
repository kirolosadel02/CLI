package com.example.commands;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class PwdCommandTest extends TestCase {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    public PwdCommandTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(PwdCommandTest.class);
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

    public void testPwdCommand() {
        PwdCommand pwdCommand = new PwdCommand();
        pwdCommand.execute(new String[] {});

        String expectedDir = System.getProperty("user.dir");

        String output = outContent.toString().trim();

        assertEquals(expectedDir, output);
    }
}
