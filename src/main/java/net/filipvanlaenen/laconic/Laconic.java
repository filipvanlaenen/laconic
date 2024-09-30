package net.filipvanlaenen.laconic;

import java.io.PrintStream;

import net.filipvanlaenen.kolektoj.OrderedCollection;

/**
 * Base class for the logging operations.
 */
public class Laconic {
    /**
     * Enumeration with the states for the logger.
     */
    private enum State {
        /**
         * Nothing has been logged yet.
         */
        EMPTY,
        /**
         * The last message logged was an error message.
         */
        ERROR_LOGGED,
        /**
         * The last message logged was a progress message.
         */
        PROGRESS_LOGGED
    }

    /**
     * The default object to log to.
     */
    public static final Laconic LOGGER = new Laconic();
    /**
     * A PrintStream to which the log messages can be appended.
     */
    private PrintStream printStream = System.err;
    /**
     * Tracks the state of the logger.
     */
    private State state = State.EMPTY;

    /**
     * Logs an error.
     *
     * @param message The message describing the error.
     * @param tokens  The tokens with log messages that are relevant for this error.
     */
    public void logError(final String message, final Token... tokens) {
        if (state != State.EMPTY) {
            printStream.println();
        }
        for (Token token : tokens) {
            OrderedCollection<String> messages = token.getMessages();
            int lastIndex = messages.size() - 1;
            for (int i = 0; i < lastIndex; i++) {
                printStream.print("‡   ");
                printStream.println(messages.getAt(i));
            }
            printStream.print("‡ ⬐ ");
            printStream.println(messages.getAt(lastIndex));
        }
        printStream.print("‡ ");
        printStream.println(message);
        state = State.ERROR_LOGGED;
    }

    /**
     * Logs a message and creates a new token.
     *
     * @param message A message to be logged.
     * @param tokens  The tokens to which this message should be added.
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
     * Logs progress.
     *
     * @param message A message to be logged.
     */
    public void logProgress(final String message) {
        if (state == State.ERROR_LOGGED) {
            printStream.println();
        }
        printStream.println(message);
        state = State.PROGRESS_LOGGED;
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
