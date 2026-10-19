/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lebogangmphahlele_part1;

import org.junit.Test;
import static org.junit.Assert.*;
/**
 *
 * @author Mathews
 */
public class MessageTest {
    
    @Test
    public void testMessageIDLength() {
        Message message = new Message(
                0, "+27718693002", "Hi Mike, can you join us tonight?");

        assertTrue(message.checkMessageID());
        assertEquals(10, message.getMessageID().length());
    }

    @Test
    public void testMessageIDGenerated() {
        Message message = new Message(
                0, "+27718693002", "Hi Mike, can you join us tonight?");

        message.setMessageIDForTesting("0012345678");

        String expected = "Message ID generated: 0012345678";
        String actual = message.getMessageIDStatus();

        assertEquals(expected, actual);
    }

    @Test
    public void testValidRecipientNumber() {
        String expected = "Cell phone number successfully captured.";

        String actual =
                Message.checkRecipientCell("+27718693002");

        assertEquals(expected, actual);
    }

    @Test
    public void testInvalidRecipientNumber() {
        String expected =
                "Cell phone number is incorrectly formatted or does not "
                + "contain an international code. Please correct the "
                + "number and try again.";

        String actual = Message.checkRecipientCell("08575975889");

        assertEquals(expected, actual);
    }

    @Test
    public void testValidMessageLength() {
        String expected = "Message ready to send.";

        String actual = Message.checkMessageLength(
                "Hi Mike, can you join us for dinner tonight?");

        assertEquals(expected, actual);
    }

    @Test
    public void testMessageLengthOver250() {
        StringBuilder longMessage = new StringBuilder();

        for (int i = 0; i < 251; i++) {
            longMessage.append("A");
        }

        String expected =
                "Message exceeds 250 characters by 1; please reduce the size.";

        String actual =
                Message.checkMessageLength(longMessage.toString());

        assertEquals(expected, actual);
    }

    // Test Case 1 from the POE: 00:0:HITONIGHT
    @Test
    public void testMessageHash() {
        Message message = new Message(
                0,
                "+27718693002",
                "Hi Mike, can you join us for dinner tonight?");

        message.setMessageIDForTesting("0012345678");

        String expected = "00:0:HITONIGHT";
        String actual = message.createMessageHash();

        assertEquals(expected, actual);
    }

    // The POE asks for the remaining hashes to be tested in a loop.
    @Test
    public void testMessageHashLoop() {
        String[] ids = {"0012345678", "0598765432"};
        String[] texts = {
            "Hi Mike, can you join us for dinner tonight?",
            "Hi Keegan, did you receive the payment?"
        };
        String[] expectedHashes = {"00:0:HITONIGHT", "05:1:HIPAYMENT"};

        for (int i = 0; i < ids.length; i++) {
            Message message = new Message(i, "+27718693002", texts[i]);
            message.setMessageIDForTesting(ids[i]);

            assertEquals(expectedHashes[i], message.createMessageHash());
        }
    }

    @Test
    public void testSendMessage() {
        Message.resetMessagesForTesting();

        Message message = new Message(
                0,
                "+27718693002",
                "Hi Mike, can you join us for dinner tonight?");

        java.io.ByteArrayInputStream testInput =
                new java.io.ByteArrayInputStream("1\n".getBytes());

        java.util.Scanner input = new java.util.Scanner(testInput);

        String expected = "Message successfully sent.";
        String actual = message.SentMessage(input);

        assertEquals(expected, actual);
        assertEquals(1, Message.returnTotalMessages());

        input.close();
    }

    @Test
    public void testDisregardMessage() {
        Message.resetMessagesForTesting();

        Message message = new Message(
                1,
                "08575975889",
                "Hi Keegan, did you receive the payment?");

        java.io.ByteArrayInputStream testInput =
                new java.io.ByteArrayInputStream("2\n".getBytes());

        java.util.Scanner input = new java.util.Scanner(testInput);

        String expected = "Press 0 to delete the message.";
        String actual = message.SentMessage(input);

        assertEquals(expected, actual);
        assertEquals(0, Message.returnTotalMessages());

        input.close();
    }

    @Test
    public void testStoreMessage() {
        Message.resetMessagesForTesting();

        Message message = new Message(
                0,
                "+27718693002",
                "Hi Mike, can you join us for dinner tonight?");

        java.io.ByteArrayInputStream testInput =
                new java.io.ByteArrayInputStream("3\n".getBytes());

        java.util.Scanner input = new java.util.Scanner(testInput);

        String expected = "Message successfully stored.";
        String actual = message.SentMessage(input);

        assertEquals(expected, actual);
        assertEquals(0, Message.returnTotalMessages());

        input.close();
    }

    @Test
    public void testTotalMessagesSent() {
        Message.resetMessagesForTesting();

        Message firstMessage = new Message(
                0, "+27718693002", "First message");

        Message secondMessage = new Message(
                1, "+27718693002", "Second message");

        java.util.Scanner sendFirst = new java.util.Scanner(
                new java.io.ByteArrayInputStream("1\n".getBytes()));

        java.util.Scanner sendSecond = new java.util.Scanner(
                new java.io.ByteArrayInputStream("1\n".getBytes()));

        firstMessage.SentMessage(sendFirst);
        secondMessage.SentMessage(sendSecond);

        assertEquals(2, Message.returnTotalMessages());

        sendFirst.close();
        sendSecond.close();
    }

    @Test
    public void testPrintMessages() {
        Message.resetMessagesForTesting();

        Message message = new Message(
                0, "+27718693002", "Hi Mike, can you join us tonight?");

        java.util.Scanner input = new java.util.Scanner(
                new java.io.ByteArrayInputStream("1\n".getBytes()));

        message.SentMessage(input);

        assertTrue(message.printMessages().contains("Hi Mike, can you join us tonight?"));

        input.close();
    }
}
