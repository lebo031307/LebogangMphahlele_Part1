/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lebogangmphahlele_part1;

import java.util.Random;
import java.util.Scanner;

/**
 * Message class for QuickChat (PROG5121 POE Parts 2 and 3).
 *
 * One Message object holds the details of one message: its ID, recipient,
 * text, hash and message number. The class also checks the details and lets
 * the user send, disregard or store the message.
 *
 * The lists of sent, disregarded and stored messages are kept in the
 * StoredMessages class (Part 3), so this class does not keep its own lists.
 *
 * References (change to Harvard style in your report):
 * - Farrell, J. Java Programming (prescribed textbook): classes, strings and
 *   loops.
 * - W3Schools. Java Strings, Java Random and Java For Loop.
 *   https://www.w3schools.com/java/
 */

public class Message {

    private String messageID;
    private String recipient;
    private String messageText;
    private String messageHash;
    private int messageNumber;

    /**
     * Creates an empty message.
     */
    public Message() {
    }

    /**
     * Creates a message. The message ID is generated automatically and the
     * message hash is created from the ID, the message number and the text.
     *
     * @param messageNumber the number of the message (the loop counter)
     * @param recipient     the recipient's cell number
     * @param messageText   the text of the message
     */
    public Message(int messageNumber, String recipient,
            String messageText) {

        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageID = generateMessageID();
        this.messageHash = createMessageHash();
    }

    // Generates a ten-digit message ID.
    // A loop adds one random digit (0-9) to the text each time it runs.
    // Reference: W3Schools Java Random and Java For Loop.
    private String generateMessageID() {
        Random random = new Random();
        String id = "";

        for (int i = 0; i < 10; i++) {
            id = id + random.nextInt(10);
        }

        return id;
    }

    /**
     * Checks that the message ID is not longer than ten characters.
     *
     * @return true if the ID exists and has ten characters or fewer
     */
    public boolean checkMessageID() {
        return messageID != null && messageID.length() <= 10;
    }

    /**
     * @return the sentence the POE asks for when the message ID is created
     */
    public String getMessageIDStatus() {
        return "Message ID generated: " + messageID;
    }

    /**
     * Checks the recipient's South African cell number. The number must be
     * +27 followed by 9 digits (the +27 replaces the leading 0), or the local
     * form: 0 followed by 9 digits, for example 0838884567. The Part 3 test
     * data uses the local form for message 4.
     *
     * @param recipient the cell number to check
     * @return a sentence that says if the number is correct or not
     */
    public static String checkRecipientCell(String recipient) {

        if (recipient != null
                && recipient.matches("^(\\+27|0)[0-9]{9}$")) {

            return "Cell phone number successfully captured.";
        } else {
            return "Cell phone number is incorrectly formatted or does not "
                    + "contain an international code. Please correct the "
                    + "number and try again.";
        }
    }

    /**
     * Checks that the message is not longer than 250 characters.
     *
     * @param message the text to check
     * @return a sentence that says if the message is short enough, or by how
     *         many characters it is too long
     */
    public static String checkMessageLength(String message) {

        if (message.length() <= 250) {
            return "Message ready to send.";
        } else {
            int extraCharacters = message.length() - 250;

            return "Message exceeds 250 characters by "
                    + extraCharacters + "; please reduce the size.";
        }
    }

    /**
     * Creates the message hash from the first two characters of the message
     * ID, the message number, and the first and last words of the message.
     * The punctuation is removed from the words and the hash is returned in
     * capital letters, for example 00:0:HITONIGHT.
     *
     * @return the message hash, or an empty text if the ID or text is missing
     */
    public String createMessageHash() {

        if (messageID == null || messageText == null
                || messageText.trim().isEmpty()) {
            return "";
        }

        String[] words = messageText.trim().split("\\s+");

        String firstWord = words[0].replaceAll("[^A-Za-z0-9]", "");
        String lastWord =
                words[words.length - 1].replaceAll("[^A-Za-z0-9]", "");

        String hash = messageID.substring(0, 2) + ":"
                + messageNumber + ":"
                + firstWord + lastWord;

        return hash.toUpperCase();
    }

    /**
     * Lets the user send, disregard or store the message, and adds the message
     * to the matching array in StoredMessages.
     * Option 1 sends the message, option 2 disregards it and option 3 stores
     * it in the JSON file.
     *
     * @param input the Scanner used to read the user's choice
     * @return a sentence that says what happened to the message
     */
    public String SentMessage(Scanner input) {

        System.out.println("Choose what to do with this message:");
        System.out.println("1) Send Message");
        System.out.println("2) Disregard Message");
        System.out.println("3) Store Message to send later");
        System.out.println("Choose an option: ");

        String choice = input.nextLine();

        if (choice.equals("1")) {

            StoredMessages.addSentMessage(
                    messageID, messageHash, recipient, messageText);

            return "Message successfully sent.";

        } else if (choice.equals("2")) {

            StoredMessages.addDisregardedMessage(
                    messageID, messageHash, recipient, messageText);

            return "Press 0 to delete the message.";

        } else if (choice.equals("3")) {

            if (storeMessage()) {
                return "Message successfully stored.";
            } else {
                return "Message could not be stored.";
            }

        } else {
            return "Invalid option. Message was not sent.";
        }
    }

    /**
     * Returns the details of all messages sent during the current run.
     *
     * @return a report of the sent messages, or a sentence that says that no
     *         messages have been sent yet
     */
    public static String printMessages() {
        return StoredMessages.getSentReport();
    }

    /**
     * @return the total number of messages sent during the current run
     */
    public static int returnTotalMessages() {
        return StoredMessages.getSentMessages().size();
    }

    /**
     * Stores this message in the JSON file (through StoredMessages).
     *
     * @return true if the message was saved, otherwise false
     */
    public boolean storeMessage() {
        return StoredMessages.saveStoredMessage(
                messageID, messageHash, recipient, messageText);
    }

    /**
     * @return the details of this message in the order the POE asks for:
     *         Message ID, Message Hash, Recipient and Message
     */
    public String printMessageDetails() {

        return "Message ID: " + messageID
                + "\nMessage Hash: " + messageHash
                + "\nRecipient: " + recipient
                + "\nMessage: " + messageText;
    }

    // Getters used by the tests.
    public String getMessageID() {
        return messageID;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getMessageText() {
        return messageText;
    }

    public String getMessageHash() {
        return messageHash;
    }

    /**
     * Lets a test use a known ID, so that the hash can be checked. The hash
     * is created again from the new ID.
     *
     * @param messageID the ID to use
     */
    public void setMessageIDForTesting(String messageID) {
        this.messageID = messageID;
        this.messageHash = createMessageHash();
    }

    /**
     * Empties all the message arrays and switches to a separate JSON file.
     * This is only used by the unit tests.
     */
    public static void resetMessagesForTesting() {
        StoredMessages.resetForTesting();
    }
}