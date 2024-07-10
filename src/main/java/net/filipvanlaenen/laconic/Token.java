package net.filipvanlaenen.laconic;

import net.filipvanlaenen.kolektoj.ModifiableOrderedCollection;
import net.filipvanlaenen.kolektoj.OrderedCollection;
import net.filipvanlaenen.kolektoj.array.ModifiableOrderedArrayCollection;
import net.filipvanlaenen.kolektoj.array.OrderedArrayCollection;

/**
 * A token to link log messages to error messages.
 */
public class Token {
    /**
     * The messages logged to this token.
     */
    private ModifiableOrderedCollection<String> messages = new ModifiableOrderedArrayCollection<String>();

    /**
     * Adds a message to this token.
     *
     * @param message A message to be added to this token.
     */
    void addMessage(final String message) {
        messages.add(message);
    }

    /**
     * Returns the messages logged to this token.
     *
     * @return The messages logged to this token.
     */
    OrderedCollection<String> getMessages() {
        return new OrderedArrayCollection<String>(messages);
    }
}