package net.filipvanlaenen.laconic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        laconic.setPrefixWithTimestamp(false);
        laconic.logError("Foo");
        assertEquals("‡ Foo\n", outputStream.toString());
    }

    /**
     * Verifies that a formatted error message can be logged.
     */
    @Test
    public void logErrorShouldLogAFormattedErrorMessage() {
        Laconic laconic = new Laconic();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        laconic.setPrintStream(printStream);
        laconic.setPrefixWithTimestamp(false);
        laconic.logError("Foo %f", 1D);
        laconic.logError("Foo %d", 1);
        laconic.logError("Foo %d", 1L);
        laconic.logError("Foo %s", "bar");
        String expected = "‡ Foo 1.000000\n\n" + "‡ Foo 1\n\n" + "‡ Foo 1\n\n" + "‡ Foo bar\n";
        assertEquals(expected, outputStream.toString());
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
        laconic.setPrefixWithTimestamp(false);
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
        laconic.setPrefixWithTimestamp(false);
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
        laconic.setPrefixWithTimestamp(false);
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
        laconic.setPrefixWithTimestamp(false);
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
        laconic.setPrefixWithTimestamp(false);
        laconic.logProgress("Foo");
        assertEquals("Foo\n", outputStream.toString());
    }

    /**
     * Verifies that formatted progress can be logged.
     */
    @Test
    public void logProgressShouldLogFormattedProgress() {
        Laconic laconic = new Laconic();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        laconic.setPrintStream(printStream);
        laconic.setPrefixWithTimestamp(false);
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
        laconic.setPrefixWithTimestamp(false);
        laconic.logProgress("Foo %f", 1D);
        laconic.logProgress("Foo %d", 1);
        laconic.logProgress("Foo %d", 1L);
        laconic.logProgress("Foo %s", "bar");
        String expected = "Foo 1.000000\n" + "Foo 1\n" + "Foo 1\n" + "Foo bar\n";
        assertEquals(expected, outputStream.toString());
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
        laconic.setPrefixWithTimestamp(false);
        laconic.logProgress("Foo");
        laconic.logProgress("Bar");
        Token token = laconic.logMessage("Baz");
        laconic.logMessage("Qux", token);
        laconic.logError("Quux", token);
        laconic.logProgress("Corge");
        assertEquals("Foo\n" + "Bar\n" + "\n" + "‡   Baz\n" + "‡ ⬐ Qux\n" + "‡ Quux\n" + "\n" + "Corge\n",
                outputStream.toString());
    }

    /**
     * Verifies that by default, all messages are prefixed with a timestamp.
     */
    @Test
    public void logMessagesShouldByDefaultBePrefixedWithATimestamp() {
        Laconic laconic = new Laconic();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        laconic.setPrintStream(printStream);
        laconic.logProgress("Foo");
        laconic.logProgress("Bar");
        Token token = laconic.logMessage("Baz");
        laconic.logMessage("Qux", token);
        laconic.logError("Quux", token);
        laconic.logProgress("Corge");
        String[] result = outputStream.toString().split("\n");
        Pattern pattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{3}[\\+-]\\d{2}:\\d{2} .*");
        for (String line : result) {
            if (line.length() > 0) {
                Matcher matcher = pattern.matcher(line);
                assertTrue(matcher.matches());
            }
        }
    }

    /**
     * Verifies that formatted log messages to an error message can be logged.
     */
    @Test
    public void logErrorShouldLogFormattedMessages() {
        Laconic laconic = new Laconic();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        laconic.setPrintStream(printStream);
        laconic.setPrefixWithTimestamp(false);
        Token token1 = laconic.logMessage("Foo %f", 1D);
        Token token2 = laconic.logMessage("Foo %d", 1);
        Token token3 = laconic.logMessage("Foo %d", 1L);
        Token token4 = laconic.logMessage("Foo %s", "bar");
        laconic.logError("Baz", token1, token2, token3, token4);
        String expected = "‡ ⬐ Foo 1.000000\n" + "‡ ⬐ Foo 1\n" + "‡ ⬐ Foo 1\n" + "‡ ⬐ Foo bar\n" + "‡ Baz\n";
        assertEquals(expected, outputStream.toString());
    }

    /**
     * Verifies that a token can be cloned.
     */
    @Test
    public void logMessageShouldCloneAToken() {
        Laconic laconic = new Laconic();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        laconic.setPrintStream(printStream);
        laconic.setPrefixWithTimestamp(false);
        Token token1 = laconic.logMessage("Foo");
        Token token2 = laconic.logMessage("Quux");
        Token token3 = laconic.logMessage(token1, "Bar", token2);
        laconic.logError("Baz", token1);
        laconic.logError("Thud", token2);
        laconic.logError("Qux", token3);
        String expected = "‡ ⬐ Foo\n" + "‡ Baz\n" + "\n" + "‡   Quux\n" + "‡ ⬐ Bar\n" + "‡ Thud\n" + "\n" + "‡   Foo\n"
                + "‡ ⬐ Bar\n" + "‡ Qux\n";
        assertEquals(expected, outputStream.toString());
    }

    /**
     * Verifies that a token can be cloned with a formatted message.
     */
    @Test
    public void logMessageShouldCloneATokenWithAFormattedMessage() {
        Laconic laconic = new Laconic();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        laconic.setPrintStream(printStream);
        laconic.setPrefixWithTimestamp(false);
        Token token1 = laconic.logMessage("Foo");
        Token token2 = laconic.logMessage(token1, "Foo %f", 1D);
        Token token3 = laconic.logMessage(token1, "Foo %d", 1);
        Token token4 = laconic.logMessage(token1, "Foo %d", 1L);
        Token token5 = laconic.logMessage(token1, "Foo %s", "bar");
        laconic.logError("Bar", token1);
        laconic.logError("Baz", token2);
        laconic.logError("Qux", token3);
        laconic.logError("Quux", token4);
        laconic.logError("Thud", token5);
        String expected = "‡ ⬐ Foo\n" + "‡ Bar\n" + "\n" + "‡   Foo\n" + "‡ ⬐ Foo 1.000000\n" + "‡ Baz\n" + "\n"
                + "‡   Foo\n" + "‡ ⬐ Foo 1\n" + "‡ Qux\n" + "\n" + "‡   Foo\n" + "‡ ⬐ Foo 1\n" + "‡ Quux\n" + "\n"
                + "‡   Foo\n" + "‡ ⬐ Foo bar\n" + "‡ Thud\n";
        ;
        assertEquals(expected, outputStream.toString());
    }
}
