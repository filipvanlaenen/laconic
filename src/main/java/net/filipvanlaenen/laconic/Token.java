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
    private final ModifiableOrderedCollection<Message> messages;

    /**
     * Default constructor creating a new token.
     */
    Token() {
        messages = new ModifiableOrderedArrayCollection<Message>();
    }

    /**
     * Constructor creating a new token based on a source token.
     *
     * @param sourceToken The token to be cloned.
     */
    Token(final Token sourceToken) {
        messages = new ModifiableOrderedArrayCollection<Message>(sourceToken.getMessages());
    }

    /**
     * Adds a message to this token.
     *
     * @param message A message to be added to this token.
     */
    void addMessage(final String message) {
        messages.add(new Message(message));
    }

    /**
     * Returns the messages logged to this token.
     *
     * @return The messages logged to this token.
     */
    OrderedCollection<Message> getMessages() {
        return new OrderedArrayCollection<Message>(messages);
    }
}
