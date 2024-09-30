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
        assertEquals("‡ Something went wrong.\n", outputStream.toString());
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
        assertEquals("‡   Something happened.\n‡ ⬐ Something else happened.\n‡ Something went wrong.\n",
                outputStream.toString());
    }

    /**
     * README example 3.
     */
    @Test
    public void readmeExample3() {
        Laconic laconic = new Laconic();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        laconic.setPrintStream(printStream);
        // Start README example 2
        Token token1 = laconic.logMessage("Something happened.");
        laconic.logMessage("Something else happened.", token1);

        Token token2 = laconic.logMessage("Started something else.");
        laconic.logMessage("Continued something else.", token2);

        laconic.logError("Something went wrong.", token1, token2);
        // End README example 2
        assertEquals("‡   Something happened.\n" + "‡ ⬐ Something else happened.\n" + "‡   Started something else.\n"
                + "‡ ⬐ Continued something else.\n" + "‡ Something went wrong.\n", outputStream.toString());
    }

    /**
     * README example 4.
     */
    @Test
    public void readmeExample4() {
        Laconic laconic = new Laconic();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        laconic.setPrintStream(printStream);
        // Start README example 2
        Token token = laconic.logMessage("Something happened.");
        laconic.logMessage("Something else happened.", token);

        laconic.logError("Something went wrong.", token);

        laconic.logError("Something else went wrong.", token);
        // End README example 2
        assertEquals("‡   Something happened.\n" + "‡ ⬐ Something else happened.\n" + "‡ Something went wrong.\n" + "\n"
                + "‡   Something happened.\n" + "‡ ⬐ Something else happened.\n" + "‡ Something else went wrong.\n",
                outputStream.toString());
    }
}
