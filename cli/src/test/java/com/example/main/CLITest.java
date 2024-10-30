package com.example.main;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * Unit test for CLI.
 */
public class CLITest extends TestCase {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public CLITest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(CLITest.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @Override
    protected void tearDown() throws Exception {
        System.setOut(originalOut);
        System.setErr(originalErr);
        super.tearDown();
    }

    /**
     * Test the CLI run method.
     */
    public void testRun() {
        String input = "pwd\nexit\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        CLI cli = new CLI();
        cli.run();

        String output = outContent.toString();
        assertTrue(output.contains("Welcome to the CLI!"));
        assertTrue(output.contains(System.getProperty("user.dir")));
        assertTrue(output.contains("Exiting..."));
    }

    // Add more tests for other commands as needed
}