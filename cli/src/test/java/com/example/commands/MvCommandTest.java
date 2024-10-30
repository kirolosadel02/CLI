package com.example.commands;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class MvCommandTest extends TestCase {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    public MvCommandTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(MvCommandTest.class);
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

    public void testMvCommand() {
        MvCommand mvCommand = new MvCommand();
        mvCommand.execute(new String[] { "source.txt", "destination.txt" });
        String output = outContent.toString();
        assertTrue(output.contains("Source file does not exist"));
    }

    public void testRenameFile() {
      TouchCommand touchCommand = new TouchCommand();
      touchCommand.execute(new String[] { "source.txt" });

      MvCommand mvCommand = new MvCommand();
      mvCommand.execute(new String[] { "source.txt", "destination.txt" });
      String output = outContent.toString();

      assertTrue(output.contains("Renamed"));

      RmCommand rmCommand = new RmCommand();
      rmCommand.execute(new String[] { "destination.txt" });
    }

    public void testMoveFile() {
      TouchCommand touchCommand = new TouchCommand();
      touchCommand.execute(new String[] { "source.txt" });

      MkdirCommand mkdirCommand = new MkdirCommand();
      mkdirCommand.execute(new String[] { "destination" });

      MvCommand mvCommand = new MvCommand();
      mvCommand.execute(new String[] { "source.txt", "destination" });
      String output = outContent.toString();

      assertTrue(output.contains("Moved"));

      RmCommand rmCommand = new RmCommand();
      rmCommand.execute(new String[] { "destination/source.txt" });
      rmCommand.execute(new String[] { "destination" });
    }
}
