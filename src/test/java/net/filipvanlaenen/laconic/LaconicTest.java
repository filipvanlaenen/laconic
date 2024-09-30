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
        laconic.setAddTimestamp(false);
        laconic.logError("Foo");
        assertEquals("‡ Foo\n", outputStream.toString());
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
        laconic.setAddTimestamp(false);
        laconic.logError("Foo");
        laconic.logError("Bar");
        assertEquals("‡ Foo\n\n‡ Bar\n", outputStream.toString());
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
        laconic.setAddTimestamp(false);
        Token token = laconic.logMessage("Foo");
        laconic.logMessage("Bar", token);
        laconic.logError("Baz", token);
        assertEquals("‡   Foo\n‡ ⬐ Bar\n‡ Baz\n", outputStream.toString());
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
        laconic.setAddTimestamp(false);
        Token token1 = laconic.logMessage("Foo");
        laconic.logMessage("Bar", token1);
        Token token2 = laconic.logMessage("Qux");
        laconic.logMessage("Quux", token2);
        laconic.logError("Baz", token1, token2);
        assertEquals("‡   Foo\n‡ ⬐ Bar\n‡   Qux\n‡ ⬐ Quux\n‡ Baz\n", outputStream.toString());
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
        laconic.setAddTimestamp(false);
        Token token = laconic.logMessage("Foo");
        laconic.logMessage("Bar", token);
        laconic.logError("Baz", token);
        laconic.logError("Qux", token);
        assertEquals("‡   Foo\n‡ ⬐ Bar\n‡ Baz\n\n‡   Foo\n‡ ⬐ Bar\n‡ Qux\n", outputStream.toString());
    }

    /**
     * Verifies that progress can be logged.
     */
    @Test
    public void logProgressShouldLogProgress() {
        Laconic laconic = new Laconic();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        laconic.setPrintStream(printStream);
        laconic.setAddTimestamp(false);
        laconic.logProgress("Foo");
        assertEquals("Foo\n", outputStream.toString());
    }

    /**
     * Verifies that progress messages can be logged.
     */
    @Test
    public void logProgressShouldLogProgressInBlock() {
        Laconic laconic = new Laconic();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        laconic.setPrintStream(printStream);
        laconic.setAddTimestamp(false);
        laconic.logProgress("Foo");
        laconic.logProgress("Bar");
        assertEquals("Foo\nBar\n", outputStream.toString());
    }

    /**
     * Verifies that progress and error messages can be logged.
     */
    @Test
    public void logProgressAndErrorsShouldLogInBlocks() {
        Laconic laconic = new Laconic();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        laconic.setPrintStream(printStream);
        laconic.setAddTimestamp(false);
        laconic.logProgress("Foo");
        laconic.logProgress("Bar");
        Token token = laconic.logMessage("Baz");
        laconic.logMessage("Qux", token);
        laconic.logError("Quux", token);
        laconic.logProgress("Corge");
        assertEquals("Foo\n" + "Bar\n" + "\n" + "‡   Baz\n" + "‡ ⬐ Qux\n" + "‡ Quux\n" + "\n" + "Corge\n",
                outputStream.toString());
    }
}
