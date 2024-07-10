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
     */
    public void logError(final String message) {
        printStream.println(message);
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
