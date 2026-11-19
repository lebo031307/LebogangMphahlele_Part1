/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lebogangmphahlele_part1;

import java.io.ByteArrayInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for the Part 3 features of the StoredMessages class
 * (PROG5121 POE Part 3).
 *
 * Before every test, the five test messages from the POE are handled in the
 * same way the program handles them (send, store or disregard), so the arrays
 * are filled by the program code and not by hard-coded values.
 *
 * References: JUnit 4 assertEquals; W3Schools Java ArrayList.
 */
public class StoredMessagesTest {

    // Test data from the POE. The message IDs are chosen here, because the
    // program normally generates them randomly. The ID of message 4 comes from
    // the POE (0838884567). The POE gives no recipient for message 4, so a
    // placeholder number is used.
    private final String id1 = "0011111111";
    private final String id2 = "1022222222";
    private final String id3 = "2033333333";
    private final String id4 = "0838884567";
    private final String id5 = "3055555555";

    private final String recipient1 = "+27834557896";
    private final String recipient2 = "+27838884567";
    private final String recipient3 = "+27834484567";
    private final String recipient4 = "+27830000004";
    private final String recipient5 = "+27838884567";

    private final String text1 = "Did you get the cake?";
    private final String text2 = "Where are you? You are late! I have asked you to be on time.";
    private final String text3 = "Yohoooo, I am at your gate.";
    private final String text4 = "It is dinner time!";
    private final String text5 = "Ok, I am leaving without you.";

    // Creates a message with a known ID so that its hash can be checked.
    private Message createMessage(int number, String id,
            String recipient, String text) {

        Message message = new Message(number, recipient, text);
        message.setMessageIDForTesting(id);
        return message;
    }

    // Handles a message with the given choice: 1 = send, 2 = disregard,
    // 3 = store. The choice is given to the program as if the user typed it.
    private void handleMessage(Message message, String choice) {

        Scanner input = new Scanner(
                new ByteArrayInputStream((choice + "\n").getBytes()));

        message.SentMessage(input);
        input.close();
    }

    // Starts every test with empty arrays and handles the five test messages.
    @Before
    public void setUp() {
        StoredMessages.resetForTesting();

        handleMessage(createMessage(0, id1, recipient1, text1), "1");  // Sent
        handleMessage(createMessage(1, id2, recipient2, text2), "3");  // Stored
        handleMessage(createMessage(2, id3, recipient3, text3), "2");  // Disregard
        handleMessage(createMessage(3, id4, recipient4, text4), "1");  // Sent
        handleMessage(createMessage(4, id5, recipient5, text5), "3");  // Stored
    }

    // Removes the test JSON file after every test.
    @After
    public void tearDown() {
        StoredMessages.resetForTesting();
    }

    // ---------- Arrays correctly populated ----------

    @Test
    public void testSentMessagesArrayPopulated() {
        ArrayList<String> expected = new ArrayList<String>(
                Arrays.asList("Did you get the cake?", "It is dinner time!"));

        assertEquals(expected, StoredMessages.getSentMessages());
    }

    @Test
    public void testDisregardedMessagesArrayPopulated() {
        ArrayList<String> expected = new ArrayList<String>(
                Arrays.asList("Yohoooo, I am at your gate."));

        assertEquals(expected, StoredMessages.getDisregardedMessages());
    }

    @Test
    public void testStoredMessagesArrayPopulated() {
        ArrayList<String> expected = new ArrayList<String>(
                Arrays.asList(text2, text5));

        assertEquals(expected, StoredMessages.getStoredMessages());
    }

    @Test
    public void testMessageIDArrayPopulated() {
        ArrayList<String> expected = new ArrayList<String>(
                Arrays.asList(id1, id2, id3, id4, id5));

        assertEquals(expected, StoredMessages.getMessageIDs());
    }

    @Test
    public void testMessageHashArrayPopulated() {
        ArrayList<String> expected = new ArrayList<String>(Arrays.asList(
                "00:0:DIDCAKE", "10:1:WHERETIME", "20:2:YOHOOOOGATE",
                "08:3:ITTIME", "30:4:OKYOU"));

        assertEquals(expected, StoredMessages.getMessageHashes());
    }

    // ---------- Read the JSON file into the stored messages array ----------

    @Test
    public void testReadJsonFileIntoStoredArray() throws IOException {

        // Write a JSON file by hand, so the test proves that the arrays are
        // filled from the file and not from what is already in memory.
        FileWriter writer = new FileWriter(StoredMessages.getJsonFileName());
        writer.write("[\n"
                + "  {\"messageID\": \"4044444444\", \"messageHash\": \"40:5:HELLOBYE\","
                + " \"recipient\": \"+27831112222\", \"message\": \"Hello and bye\"}\n"
                + "]\n");
        writer.close();

        assertTrue(StoredMessages.loadStoredMessages());

        ArrayList<String> expected = new ArrayList<String>(
                Arrays.asList("Hello and bye"));

        assertEquals(expected, StoredMessages.getStoredMessages());
    }

    @Test
    public void testSpecialCharactersSurviveTheJsonFile() {

        // A message with quotation marks and a backslash is stored in the
        // JSON file and then read back, and it must be the same.
        String tricky = "He said \"hi\" and left \\ quickly";
        Message message = createMessage(5, "5066666666", "+27835556666", tricky);
        handleMessage(message, "3");

        assertTrue(StoredMessages.loadStoredMessages());

        assertTrue(StoredMessages.getStoredMessages().contains(tricky));
    }

    @Test
    public void testReadMissingJsonFile() {
        StoredMessages.resetForTesting();    // deletes the test JSON file

        assertFalse(StoredMessages.loadStoredMessages());
    }

    // ---------- Display the longest message ----------

    @Test
    public void testLongestStoredMessage() {
        assertEquals(text2, StoredMessages.getLongestStoredMessage());
    }

    // ---------- Search for a message ID ----------

    @Test
    public void testSearchByMessageID() {
        assertEquals("It is dinner time!", StoredMessages.searchByMessageID("0838884567"));
    }

    @Test
    public void testSearchByMessageIDGivesRecipient() {
        assertEquals(recipient2, StoredMessages.getRecipientByMessageID(id2));
    }

    @Test
    public void testSearchByMessageIDNotFound() {
        assertNull(StoredMessages.searchByMessageID("9999999999"));
    }

    // ---------- Search for all messages to a recipient ----------

    @Test
    public void testSearchByRecipient() {
        ArrayList<String> expected = new ArrayList<String>(Arrays.asList(
                "Where are you? You are late! I have asked you to be on time.",
                "Ok, I am leaving without you."));

        assertEquals(expected, StoredMessages.searchByRecipient("+27838884567"));
    }

    @Test
    public void testSearchByRecipientIgnoresDisregarded() {
        // Message 3 was disregarded, so it must not be found.
        assertTrue(StoredMessages.searchByRecipient(recipient3).isEmpty());
    }

    // ---------- Sender and recipient of all stored messages ----------

    @Test
    public void testSendersAndRecipients() {
        ArrayList<String> expected = new ArrayList<String>(Arrays.asList(
                "Sender: Lebogang Mphahlele | Recipient: +27838884567",
                "Sender: Lebogang Mphahlele | Recipient: +27838884567"));

        assertEquals(expected,
                StoredMessages.getSendersAndRecipients("Lebogang Mphahlele"));
    }

    // ---------- Delete a message using the message hash ----------

    @Test
    public void testDeleteMessageByHash() {
        String expected = "Message: \"Where are you? You are late! "
                + "I have asked you to be on time\" successfully deleted.";

        assertEquals(expected, StoredMessages.deleteMessageByHash("10:1:WHERETIME"));

        // The message must be gone from the array ...
        assertEquals(new ArrayList<String>(Arrays.asList(text5)),
                StoredMessages.getStoredMessages());

        // ... and from the JSON file.
        assertTrue(StoredMessages.loadStoredMessages());
        assertEquals(new ArrayList<String>(Arrays.asList(text5)),
                StoredMessages.getStoredMessages());
    }

    @Test
    public void testDeleteMessageByHashNotFound() {
        assertEquals("Message hash not found.",
                StoredMessages.deleteMessageByHash("99:9:NOTHERE"));
    }

    // ---------- Display the report ----------

    @Test
    public void testSentReport() {
        String report = StoredMessages.getSentReport();

        // Both sent messages show their hash, recipient and message.
        assertTrue(report.contains("Message Hash: 00:0:DIDCAKE"));
        assertTrue(report.contains("Recipient: +27834557896"));
        assertTrue(report.contains("Message: Did you get the cake?"));
        assertTrue(report.contains("Message Hash: 08:3:ITTIME"));
        assertTrue(report.contains("Message: It is dinner time!"));

        // A stored message must not be in the sent report.
        assertFalse(report.contains("Where are you?"));
    }

    @Test
    public void testStoredReport() {
        String report = StoredMessages.getStoredReport();

        assertTrue(report.contains("Message ID: 1022222222"));
        assertTrue(report.contains("Message Hash: 10:1:WHERETIME"));
        assertTrue(report.contains("Recipient: +27838884567"));
        assertTrue(report.contains("Message: Ok, I am leaving without you."));
        assertFalse(report.contains("Did you get the cake?"));
    }
}