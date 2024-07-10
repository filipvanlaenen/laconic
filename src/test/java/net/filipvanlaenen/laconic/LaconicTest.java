package net.filipvanlaenen.laconic;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.Test;

/**
 * Unit tests on the {@link net.filipvanlaen.laconic.Laconic} class.
 */
public class LaconicTest {
    /**
     * Verifies that an error message can be logged.
     */
    @Test
    public void logErrorShouldLogAnErrorMessage() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        Laconic.LOGGER.setPrintStream(printStream);
        Laconic.LOGGER.logError("Something went wrong.");
        assertEquals("Something went wrong.\n", outputStream.toString());
    }

    /**
     * Verifies that two error messages can be logged.
     */
    @Test
    public void logErrorShouldLogTwoErrorMessages() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        Laconic.LOGGER.setPrintStream(printStream);
        Laconic.LOGGER.logError("Something went wrong.");
        Laconic.LOGGER.logError("Something else went wrong.");
        assertEquals("Something went wrong.\nSomething else went wrong.\n", outputStream.toString());
    }
}
