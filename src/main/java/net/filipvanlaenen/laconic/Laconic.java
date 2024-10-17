package net.filipvanlaenen.laconic;

import java.io.PrintStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import net.filipvanlaenen.kolektoj.OrderedCollection;

/**
 * Base class for the logging operations.
 */
public class Laconic {
    /**
     * The format for the timestamp.
     */
    private static final SimpleDateFormat TIMESTAMP_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

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
     * Whether messages should be prefixed with a timestamp.
     */
    private boolean prefixWithTimestamp = true;

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
            OrderedCollection<Message> messages = token.getMessages();
            int lastIndex = messages.size() - 1;
            for (int i = 0; i < lastIndex; i++) {
                Message logMessage = messages.getAt(i);
                printTimestamp(logMessage.timestamp());
                printStream.print("‡   ");
                printStream.println(logMessage.message());
            }
            Message logMessage = messages.getAt(lastIndex);
            printTimestamp(logMessage.timestamp());
            printStream.print("‡ ⬐ ");
            printStream.println(logMessage.message());
        }
        printTimestamp();
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
        printTimestamp();
        printStream.println(message);
        state = State.PROGRESS_LOGGED;
    }

    /**
     * Prints the current timestamp to the internal print stream.
     */
    private void printTimestamp() {
        printTimestamp(new Timestamp(System.currentTimeMillis()));
    }

    /**
     * Prints a timestamp to the internal print stream.
     *
     * @param timestamp A timestamp.
     */
    private void printTimestamp(final Timestamp timestamp) {
        if (prefixWithTimestamp) {
            printStream.print(TIMESTAMP_FORMAT.format(timestamp));
            printStream.print(" ");
        }
    }

    /**
     * Sets whether the messages should be prefixed with a timestamp.
     *
     * @param prefixWithTimestamp Whether the messages should be prefixed with a timestamp.
     */
    public void setPrefixWithTimestamp(final boolean prefixWithTimestamp) {
        this.prefixWithTimestamp = prefixWithTimestamp;
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
