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
    public void logErrorShouldLogLogMessagesAlongAnErrorMessage() {
        Laconic laconic = new Laconic();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        laconic.setPrintStream(printStream);
        Token token = laconic.logMessage("Foo");
        laconic.logMessage("Bar", token);
        laconic.logError("Baz", token);
        assertEquals(" Foo\n⬐Bar\nBaz\n", outputStream.toString());
    }

    /**
     * Verifies that two times two log messages can be logged along an error message.
     */
    @Test
    public void logErrorShouldLogSetsOfLogMessagesAlongAnErrorMessage() {
        Laconic laconic = new Laconic();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        laconic.setPrintStream(printStream);
        Token token1 = laconic.logMessage("Foo");
        laconic.logMessage("Bar", token1);
        Token token2 = laconic.logMessage("Qux");
        laconic.logMessage("Quux", token2);
        laconic.logError("Baz", token1, token2);
        assertEquals(" Foo\n⬐Bar\n Qux\n⬐Quux\nBaz\n", outputStream.toString());
    }

    /**
     * Verifies that two errors based on the same log messages are logged separately.
     */
    @Test
    public void logErrorShouldLogErrorMessagesSeparatelyWithTheSameLogMessages() {
        Laconic laconic = new Laconic();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        laconic.setPrintStream(printStream);
        Token token = laconic.logMessage("Foo");
        laconic.logMessage("Bar", token);
        laconic.logError("Baz", token);
        laconic.logError("Qux", token);
        assertEquals(" Foo\n⬐Bar\nBaz\n\n Foo\n⬐Bar\nQux\n", outputStream.toString());
    }
}
