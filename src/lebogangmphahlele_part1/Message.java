/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lebogangmphahlele_part1;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
/**
 *
 * @author Mathews
 */

public class Message {
    
    private String messageID;
    private String recipient;
    private String messageText;
    private String messageHash;
    private int messageNumber;

    private static int totalMessagesSent = 0;
    private static ArrayList<Message> sentMessages =
            new ArrayList<Message>();

    private static ArrayList<Message> storedMessages =
            new ArrayList<Message>();

    public Message() {
    }

    public Message(int messageNumber, String recipient,
            String messageText) {

        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageID = generateMessageID();
        this.messageHash = createMessageHash();
    }

    // Generate a ten-digit message ID.
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

    // Check that the message ID is no longer than ten characters.
    public boolean checkMessageID() {
        return messageID != null && messageID.length() <= 10;
    }

    // Returns the text the POE asks for when the message ID is created.
    public String getMessageIDStatus() {
        return "Message ID generated: " + messageID;
    }

    // Check the recipient's South African international number.
    // +27 replaces the leading 0, so it is +27 followed by 9 digits.
    public static String checkRecipientCell(String recipient) {

        if (recipient != null
                && recipient.matches("^\\+27[0-9]{9}$")) {

            return "Cell phone number successfully captured.";
        } else {
            return "Cell phone number is incorrectly formatted or does not "
                    + "contain an international code. Please correct the "
                    + "number and try again.";
        }
    }

    // Check that the message is not longer than 250 characters.
    public static String checkMessageLength(String message) {

        if (message.length() <= 250) {
            return "Message ready to send.";
        } else {
            int extraCharacters = message.length() - 250;

            return "Message exceeds 250 characters by "
                    + extraCharacters + "; please reduce the size.";
        }
    }

    // Create a hash using the first two ID characters,
    // the message number, and the first and last words.
    // The hash is returned in capital letters, e.g. 00:0:HITONIGHT.
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

    // Allow the user to send, disregard or store the message.
    public String SentMessage(Scanner input) {

        System.out.println("Choose what to do with this message:");
        System.out.println("1) Send Message");
        System.out.println("2) Disregard Message");
        System.out.println("3) Store Message to send later");
        System.out.println("Choose an option: ");

        String choice = input.nextLine();

        if (choice.equals("1")) {

            sentMessages.add(this);
            totalMessagesSent++;

            return "Message successfully sent.";

        } else if (choice.equals("2")) {

            return "Press 0 to delete the message.";

        } else if (choice.equals("3")) {

            storedMessages.add(this);

            if (storeMessage()) {
                return "Message successfully stored.";
            } else {
                storedMessages.remove(this);
                return "Message could not be stored.";
            }

        } else {
            return "Invalid option. Message was not sent.";
        }
    }

    // Return all messages sent during the current run.
    public String printMessages() {

        if (sentMessages.isEmpty()) {
            return "No messages have been sent yet.";
        }

        String output = "";

        for (int i = 0; i < sentMessages.size(); i++) {
            output = output
                    + sentMessages.get(i).printMessageDetails()
                    + "\n";
        }

        return output;
    }

    // Return the total number of messages sent.
    public static int returnTotalMessages() {
        return totalMessagesSent;
    }

    // Store the message in a JSON file.
    // File writing adapted from: W3Schools "Java Create and Write To Files"
    // (https://www.w3schools.com/java/java_files_create.asp).
    // The JSON layout follows the format described at https://www.json.org
    public boolean storeMessage() {

        try {
            FileWriter writer = new FileWriter("messages.json");

            writer.write("[\n");

            for (int i = 0; i < storedMessages.size(); i++) {

                Message message = storedMessages.get(i);

                writer.write("  {\n");
                writer.write("    \"messageID\": \""
                        + escapeJson(message.messageID) + "\",\n");
                writer.write("    \"messageHash\": \""
                        + escapeJson(message.messageHash) + "\",\n");
                writer.write("    \"recipient\": \""
                        + escapeJson(message.recipient) + "\",\n");
                writer.write("    \"message\": \""
                        + escapeJson(message.messageText) + "\",\n");
                writer.write("    \"messageNumber\": "
                        + message.messageNumber + "\n");
                writer.write("  }");

                if (i < storedMessages.size() - 1) {
                    writer.write(",");
                }

                writer.write("\n");
            }

            writer.write("]\n");
            writer.close();

            return true;

        } catch (IOException e) {
            System.out.println("Error writing to the JSON file.");
            return false;
        }
    }

    // Escape characters that could break the JSON format.
    private String escapeJson(String text) {

        return text.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }

    // Display the required message details.
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

    // Allow the test to use a known ID for hash verification.
    public void setMessageIDForTesting(String messageID) {
        this.messageID = messageID;
        this.messageHash = createMessageHash();
    }

    public static void resetMessagesForTesting() {
        totalMessagesSent = 0;
        sentMessages.clear();
        storedMessages.clear();
    }
}