package net.filipvanlaenen.laconic;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.Test;

/**
 * Unit tests verifying that the examples in the README file are still valid and up-to-date.
 */
public class ReadmeExamplesTest {
    /**
     * README example 1.
     */
    @Test
    public void readmeExample1() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        Laconic.LOGGER.setPrintStream(printStream);
        // Start README example 1
        Laconic.LOGGER.logError("Something went wrong.");
        // End README example 1
        assertEquals("Something went wrong.\n", outputStream.toString());
    }

    /**
     * README example 2.
     */
    @Test
    public void readmeExample2() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        Laconic.LOGGER.setPrintStream(printStream);
        // Start README example 2
        Token token = Laconic.LOGGER.logMessage("Something happened.");
        Laconic.LOGGER.logMessage("Something else happened.", token);
        Laconic.LOGGER.logError("Something went wrong.", token);
        // End README example 2
        assertEquals("Something happened.\nSomething else happened.\nSomething went wrong.\n", outputStream.toString());
    }
}
