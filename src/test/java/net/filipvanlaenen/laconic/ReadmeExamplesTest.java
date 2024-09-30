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
        Laconic laconic = new Laconic();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        laconic.setPrintStream(printStream);
        // Start README example 1
        laconic.logError("Something went wrong.");
        // End README example 1
        assertEquals("Something went wrong.\n", outputStream.toString());
    }

    /**
     * README example 2.
     */
    @Test
    public void readmeExample2() {
        Laconic laconic = new Laconic();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        laconic.setPrintStream(printStream);
        // Start README example 2
        Token token = laconic.logMessage("Something happened.");
        laconic.logMessage("Something else happened.", token);
        laconic.logError("Something went wrong.", token);
        // End README example 2
        assertEquals(" Something happened.\n⬐Something else happened.\nSomething went wrong.\n",
                outputStream.toString());
    }
}
