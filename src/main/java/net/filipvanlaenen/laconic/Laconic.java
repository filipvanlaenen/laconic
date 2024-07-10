package net.filipvanlaenen.laconic;

import java.io.PrintStream;

/**
 * Base class for the logging operations.
 */
public class Laconic {
    /**
     * The default object to log to.
     */
    public static final Laconic LOGGER = new Laconic();
    /**
     * A PrintStream to which the log messages can be appended.
     */
    private PrintStream printStream = System.err;

    /**
     * Logs an error.
     *
     * @param message The message describing the error.
     * @param tokens  The tokens with log messages that are relevant for this error.
     */
    public void logError(final String message, final Token... tokens) {
        for (Token token : tokens) {
            for (String m : token.getMessages()) {
                printStream.println(m);
            }
        }
        printStream.println(message);
    }

    /**
     * Logs a message and creates a new token.
     *
     * @param message A message to be logged.
     * @param tokens The tokens to which this message should be added.
     * @return A token for this log message.
     */
    public Token logMessage(final String message, final Token... tokens) {
        Token token = new Token();
        token.addMessage(message);
        for (Token t : tokens) {
            t.addMessage(message);
        }
        return token;
    }

    /**
     * Sets the PrintStream to which the log messages can be appended.
     *
     * @param printStream The PrintStream to which the log messages can be appended.
     */
    public void setPrintStream(final PrintStream printStream) {
        this.printStream = printStream;
    }
}
