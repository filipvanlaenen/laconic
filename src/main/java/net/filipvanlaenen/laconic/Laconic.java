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
    private PrintStream appender = System.err;

    /**
     * Logs an error to the appender.
     *
     * @param message The message describing the error.
     */
    public void logError(final String message) {
        appender.println(message);
    }
}
