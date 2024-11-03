package net.filipvanlaenen.laconic;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
     * Verifies that when a token is cloned, the original token doesn't receive the messages on the new token.
     */
    @Test
    public void getMessagesIsEmptyOnOriginalTokenAfterAMessageIsAddedToTheClonedToken() {
        Token originalToken = new Token();
        Token newToken = new Token(originalToken);
        newToken.addMessage("Something happend.");
        assertTrue(originalToken.getMessages().isEmpty());
    }

    /**
     * Verifies that when a token is cloned, the new token inherits all the messages.
     */
    @Test
    public void getMessagesReturnsAllTheMessagesFromTheOriginalTokenAfterCloning() {
        Token originalToken = new Token();
        originalToken.addMessage("Something happend.");
        Token newToken = new Token(originalToken);
        OrderedCollection<Message> messages = newToken.getMessages();
        assertEquals("Something happend.", messages.getAt(0).message());
    }

    /**
     * Verifies that when a message has been added to a token, it is returned by getMessages.
     */
    @Test
    public void getMessagesShouldReturnALoggedMessage() {
        Token token = new Token();
        token.addMessage("Something happend.");
        OrderedCollection<Message> messages = token.getMessages();
        assertEquals("Something happend.", messages.getAt(0).message());
    }

    /**
     * Verifies that when two messages have been added to a token, they are returned by getMessages.
     */
    @Test
    public void getMessagesShouldReturnTwoLoggedMessages() {
        Token token = new Token();
        token.addMessage("Something happend.");
        token.addMessage("Something else happend.");
        OrderedCollection<Message> messages = token.getMessages();
        assertEquals("Something happend.", messages.getAt(0).message());
        assertEquals("Something else happend.", messages.getAt(1).message());
    }
}
