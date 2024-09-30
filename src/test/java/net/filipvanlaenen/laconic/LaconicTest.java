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
        Laconic laconic = new Laconic();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        laconic.setPrintStream(printStream);
        laconic.logError("Foo");
        assertEquals("Foo\n", outputStream.toString());
    }

    /**
     * Verifies that two error messages can be logged.
     */
    @Test
    public void logErrorShouldLogTwoErrorMessages() {
        Laconic laconic = new Laconic();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        laconic.setPrintStream(printStream);
        laconic.logError("Foo");
        laconic.logError("Bar");
        assertEquals("Foo\n\nBar\n", outputStream.toString());
    }

    /**
     * Verifies that two log messages can be logged along an error message.
     */
    @Test
    public void logErrorShouldLogALogMessageAlongAnErrorMessage() {
        Laconic laconic = new Laconic();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        laconic.setPrintStream(printStream);
        Token token = laconic.logMessage("Foo");
        laconic.logMessage("Bar", token);
        laconic.logError("Baz", token);
        assertEquals(" Foo\n⬐Bar\nBaz\n", outputStream.toString());
    }
}
