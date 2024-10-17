package net.filipvanlaenen.laconic;

import java.sql.Timestamp;

/**
 * A record holding the information about a log message.
 *
 * @param message   The message.
 * @param timestamp The timestamp.
 */
record Message(String message, Timestamp timestamp) {
    /**
     * Constructor taking only a message and adding the current time as the timestamp for the log message.
     *
     * @param message The message.
     */
    Message(final String message) {
        this(message, new Timestamp(System.currentTimeMillis()));
    }
}
