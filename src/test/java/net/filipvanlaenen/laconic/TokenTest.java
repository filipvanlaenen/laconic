package net.filipvanlaenen.laconic;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import net.filipvanlaenen.kolektoj.OrderedCollection;

/**
 * Unit tests on the {@link net.filipvanlaen.laconic.Token} class.
 */
public class TokenTest {
    /**
     * Verifies that by default, no messages are registered on a token.
     */
    @Test
    public void getMessagesShouldReturnEmptyCollectionByDefault() {
        assertTrue(new Token().getMessages().isEmpty());
    }

    /**
     * Verifies that when a message has been added to a token, it is returned by getMessages.
     */
    @Test
    public void getMessagesShouldReturnALoggedMessage() {
        Token token = new Token();
        token.addMessage("Something happend.");
        assertTrue(OrderedCollection.of("Something happend.").containsSame(token.getMessages()));
    }

    /**
     * Verifies that when two messages have been added to a token, they are returned by getMessages.
     */
    @Test
    public void getMessagesShouldReturnTwoLoggedMessages() {
        Token token = new Token();
        token.addMessage("Something happend.");
        token.addMessage("Something else happend.");
        assertTrue(OrderedCollection.of("Something happend.", "Something else happend.")
                .containsSame(token.getMessages()));
    }
}
