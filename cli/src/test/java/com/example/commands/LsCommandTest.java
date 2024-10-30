package com.example.commands;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.File;

public class LsCommandTest extends TestCase {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    public LsCommandTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(LsCommandTest.class);
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

    public void testLsCommand() {
        LsCommand lsCommand = new LsCommand(false, false);
        lsCommand.execute(new String[] {});
        String output = outContent.toString();

        File[] files = new File(System.getProperty("user.dir")).listFiles();
        Integer nonHiddenCount = 0;
        for (File file : files) {
            if (!file.isHidden()) {
                nonHiddenCount++;
            }
        }

        Integer countOutput = output.split("\n").length;
        assertEquals(nonHiddenCount, countOutput);
    }


    public void testLsCommandWithHidden() {
        LsCommand lsCommand = new LsCommand(true, false);
        lsCommand.execute(new String[] {});
        String output = outContent.toString();

        Integer countOutput = output.split("\n").length;
        File[] files = new File(System.getProperty("user.dir")).listFiles();
        assertTrue(countOutput == files.length);
    }


    public void testLsCommandWithReverse() {
        LsCommand lsCommand = new LsCommand(true, true);
        lsCommand.execute(new String[] {});
        String output = outContent.toString();

        File[] files = new File(System.getProperty("user.dir")).listFiles();
        Integer count = files.length;

        String[] outputLines = output.split("\n");
        assertTrue(outputLines.length == count);
    }
}
